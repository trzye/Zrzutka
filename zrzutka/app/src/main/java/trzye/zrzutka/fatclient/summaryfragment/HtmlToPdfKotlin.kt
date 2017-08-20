package trzye.zrzutka.fatclient.summaryfragment

//import org.owasp.html.HtmlPolicyBuilder
//import org.owasp.html.PolicyFactory
import android.os.Environment
import android.util.Log
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.tool.xml.XMLWorkerFontProvider
import com.itextpdf.tool.xml.XMLWorkerHelper
import trzye.zrzutka.model.dto.web.ContributionDTO
import java.io.ByteArrayInputStream
//import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
//import java.nio.charset.Charset
//import java.nio.charset.StandardCharsets
//import com.itextpdf.text.pdf.BaseFont


class HtmlToPdf {
    @Throws(IOException::class, DocumentException::class) /*, DocumentException*/
    fun createPdf(filePath: String, html: String) {
        val document = Document()
        val writer = PdfWriter.getInstance(document, FileOutputStream(filePath))
        document.open()
        val inputStream = ByteArrayInputStream(html.toByteArray(Charsets.UTF_8))
//        val bfComic = BaseFont.createFont("/assets/fonts/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)
//        val font = Font(bfComic, 24f, Font.NORMAL, BaseColor.BLACK)

        val fontProvider = XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS)
        fontProvider.register("/assets/fonts/FreeSans.ttf")
        for (s in fontProvider.registeredFamilies) {
            Log.i("FONTYYY", s)
        }
        FontFactory.setFontImp(fontProvider)

        XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream, null, Charsets.UTF_8, fontProvider)
//        document.add(Paragraph("żółw lololo", font))
        document.close()
    }
}


fun ContributionDTO.createHtml(): String {
    val htmlBuilder = StringBuilder("""
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
        <title>Zrzutka: ${this.title.sanitize()}</title>
    <style type="text/css">body {
        font-family: Arial Unicode MS, FreeSans;
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
        font-size: 24px;
    }

    h3 {
        color: #F90B6D;
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

fun String.sanitize() : String {
    return this
}

//object HtmlPolicy {
//    val policy: PolicyFactory by lazy { HtmlPolicyBuilder().toFactory() }
//    fun sanitize(string: String) = policy.sanitize(string)!!
//}