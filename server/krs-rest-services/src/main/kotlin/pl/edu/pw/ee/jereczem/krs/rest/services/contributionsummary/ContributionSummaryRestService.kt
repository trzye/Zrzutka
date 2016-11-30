package pl.edu.pw.ee.jereczem.krs.rest.services.contributionsummary

import pl.edu.pw.ee.jereczem.krs.business.ContributionSummaryController
import pl.edu.pw.ee.jereczem.krs.model.ContributionSummaryDTO
import pl.edu.pw.ee.jereczem.krs.model.ContributorDTO
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
        if(summary != null)
            return  summary.createHtml()
        else
            throw WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND)
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    fun saveSummary(contribution: ContributionSummaryDTO)
            = contributionSummaryController.saveContributionSummary(contribution)

}

private fun ContributionSummaryDTO.createHtml(): String {
    val htmlBuilder = StringBuilder("""
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${this.title}</title>
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
        }</style>
    </head>
    <body>
        <h1>${this.title}</h1>
        <p>Czas: ${this.subtitle}</p>
    """)

    if(this.preciseMode == "true")
        htmlBuilder.append("""
        <p>Tryb: dokładny</p>
    """) else {
        htmlBuilder.append("""
        <p>Tryb: zaokrąglony</p>
    """)
    }

    if(this.contributors.isNotEmpty()) {
        htmlBuilder.append("<h2>Uczestnicy</h2>")
        htmlBuilder.append("<ol>")
        this.contributors.forEach {
            htmlBuilder.append("""
                    <li>${it.nickname}</li>
            """)
            if(it.contactInformation.isNotBlank() || it.paymentInformation.isNotBlank()){
                htmlBuilder.append("<ul>")
                if(it.contactInformation.isNotBlank()){
                    htmlBuilder.append("""
                        <li>Dane kontaktowe: ${it.contactInformation}</li>
                    """)
                }
                if(it.paymentInformation.isNotBlank()){
                    htmlBuilder.append("""
                        <li>Dane płatności: ${it.paymentInformation}</li>
                    """)
                }
                htmlBuilder.append("</ul>")
            }
        }
        htmlBuilder.append("</ol>")
    }

    if(this.purchases.isNotEmpty()){
        htmlBuilder.append("<h2>Zakupy</h2>")
        htmlBuilder.append("<ol>")
        this.purchases.forEach {
            htmlBuilder.append("""
                    <li>${it.name} - koszt: ${it.price}</li>
            """)
            htmlBuilder.append("""
            <table cellpadding="6">
            <tr>
                <td><b>Uczestnik</b></td>
                <td><b>Należność</b></td>
                <td><b>Wpłata</b></td>
            </tr>
            """)
            it.charges.forEach {
                htmlBuilder.append("""
                <tr>
                    <td>${it.charged}</td>
                    <td>${it.toPay}</td>
                    <td>${it.paid}</td>
                </tr>
                """)
            }
            htmlBuilder.append("</table>")
        }
        htmlBuilder.append("</ol>")
    }

    if(this.debts.isNotEmpty()){
        htmlBuilder.append("<h2>Rozliczenie</h2>")
        htmlBuilder.append("""
            <table cellpadding="6">
            <tr>
                <td><b>Kto?</b></td>
                <td><b>Komu?</b></td>
                <td><b>Ile?</b></td>
            </tr>
        """)
        this.debts.forEach {
            htmlBuilder.append("""
            <tr>
                <td>${it.whoPays}</td>
                <td>${it.toWhom}</td>
                <td>${it.amount}</td>
            </tr>
            """)
        }
        htmlBuilder.append("</table>")
    }

    htmlBuilder.append("""
        </body>
        </html>
    """)
    return htmlBuilder.toString()
}

