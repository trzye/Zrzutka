package pl.edu.pw.ee.jereczem.krs.rest.services.contributionsummary

import com.google.gson.Gson
import org.owasp.html.HtmlPolicyBuilder
import pl.edu.pw.ee.jereczem.krs.business.ContributionSummaryController
import pl.edu.pw.ee.jereczem.krs.model.*
import pl.edu.pw.ee.jereczem.krs.rest.BeanProvider
import java.net.HttpURLConnection
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/")
class ContributionSummaryRestService {

    private var contributionSummaryController = BeanProvider.provide(ContributionSummaryController::class.java)

    @GET
    @Path("/{summaryId}")
    @Produces(MediaType.TEXT_HTML)
    fun getSummaryPage(@PathParam("summaryId") summaryId: Long) : String {
        val summary = contributionSummaryController.getContributionSummaryBy(summaryId)
        val summ =  ContributionSummaryDTO(
                title = "Zrzutka",
                subtitle = "20.01 - 12.03",
                preciseMode = "false",
                contributors = listOf(
                        ContributorDTO("andrzej", "gg 123456", "$$$"),
                        ContributorDTO("Ola", "gg 223456", "$$2$"),
                        ContributorDTO("maciek", "gg 423456", "$1$$")
                ),
                purchases = listOf(
                        PurchaseDTO("Coś fajnego", "100 zł", listOf(
                                ChargeDTO("Andrzej", "20", "10"),
                                ChargeDTO("Ola", "20", "10")
                        ))
                ),
                debts = listOf(
                        DebtDTO("Andrzej", "Ola", "20"),
                        DebtDTO("Maciej", "Ola", "20")
                )
        )
        return Gson().toJson(summ)
        if(summary != null)
            return  summary.toString()//.createHtml()
        else
            throw WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND)
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    fun saveSummary(contribution: ContributionSummaryDTO)
            = contributionSummaryController.saveContributionSummary(contribution)

}

object HtmlPolicy {
    val policy by lazy { HtmlPolicyBuilder().toFactory() }
    fun sanitize(string: String) = policy.sanitize(string)
}

fun String.sanitize() : String {
    return HtmlPolicy.sanitize(this)
}

private fun ContributionSummaryDTO.createHtml(): String {
    val htmlBuilder = StringBuilder("""
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Zrzutka: ${this.title.sanitize()}</title>
    <style type="text/css">body {
        margin: 40px auto;
        max-width: 650px;
        line-height: 1.6;
        font-size: 18px;
        color: #444;
        padding: 0 10px
    }

    h1, h2, h3 {
        line-height: 1.2
    }

    table, td, th {
        border: 1px solid #ddd;
        text-align: left;
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th, td {
        padding: 15px;
    }

    h2 {
        color: #F90B6D;
        font-family: 'Open Sans', sans-serif;
        font-size: 24px;
    }

    h3 {
        color: #F90B6D;
        font-family: 'Open Sans', sans-serif;
        font-size: 16px;
    }

    th {
        background-color: palevioletred;
        color: white;
    }

    </style>
    </head>
    <body>
        <h2>Dane zrzutki</h2>
    """)

    val type = if(this.preciseMode == "true") "dokładny" else "zaokrąglenie"

    htmlBuilder.append("""
    <table border="1" cellpadding="5">
    <tr>
        <th>Nazwa zrzutki</th>
        <th>Data</th>
        <th>Tryb</th>
    </tr>
    <tr>
        <td>${title.sanitize()}</td>
        <td>${subtitle.sanitize()}</td>
        <td>$type</td>
    </tr>
    </table>
    """)


    if(this.debts.isNotEmpty()){
        htmlBuilder.append("<h2>Rozliczenie</h2>")
        htmlBuilder.append("""
            <table border="1" cellpadding="5">
            <tr>
                <th>Kto?</th>
                <th>Komu?</th>
                <th>Ile?</th>
            </tr>
        """)
        this.debts.forEach {
            htmlBuilder.append("""
            <tr>
                <td>${it.whoPays.sanitize()}</td>
                <td>${it.toWhom.sanitize()}</td>
                <td>${it.amount.sanitize()}</td>
            </tr>
            """)
        }
        htmlBuilder.append("</table>")
    }

    if(this.contributors.filter { it.contactInformation.isNotBlank() }.isNotEmpty()){
        htmlBuilder.append("<h2>Dane kontaktowe</h2>")
        htmlBuilder.append("""
            <table border="1" cellpadding="5">
            <tr>
                <th>Uczestnik</th>
                <th>Kontakt</th>
            </tr>
        """)
        this.contributors.filter { it.contactInformation.isNotBlank() }.forEach {
            htmlBuilder.append("""
            <tr>
                <td>${it.nickname.sanitize()}</td>
                <td>${it.contactInformation.sanitize()}</td>
            </tr>
            """)
        }
        htmlBuilder.append("</table>")
    }

    if(this.contributors.filter { it.paymentInformation.isNotBlank() }.isNotEmpty()){
        htmlBuilder.append("<h2>Dane płatności</h2>")
        htmlBuilder.append("""
            <table border="1" cellpadding="5">
            <tr>
                <th>Uczestnik</th>
                <th>Płatność</th>
            </tr>
        """)
        this.contributors.filter { it.paymentInformation.isNotBlank() }.forEach {
            htmlBuilder.append("""
            <tr>
                <td>${it.nickname.sanitize()}</td>
                <td>${it.paymentInformation.sanitize()}</td>
            </tr>
            """)
        }
        htmlBuilder.append("</table>")
    }

    if(this.purchases.isNotEmpty()){
        htmlBuilder.append("<h2>Zakupy</h2>")
        this.purchases.forEach {
            htmlBuilder.append("""
                    <h3>${it.name.sanitize()} - ${it.price.sanitize()}</h3>
            """)
            htmlBuilder.append("""
            <table border="1" cellpadding="5">
            <tr>
                <th>Uczestnik</th>
                <th>Należność</th>
                <th>Wpłata</th>
            </tr>
            """)
            it.charges.forEach {
                htmlBuilder.append("""
                <tr>
                    <td>${it.charged.sanitize()}</td>
                    <td>${it.toPay.sanitize()}</td>
                    <td>${it.paid.sanitize()}</td>
                </tr>
                """)
            }
            htmlBuilder.append("</table>")
        }
    }

    htmlBuilder.append("""
        </body>
        </html>
    """)
    return htmlBuilder.toString()
}

fun main(args: Array<String>) {
    String
}
