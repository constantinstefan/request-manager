package fii.request.manager.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;

@Service
public class HtmlToPdfConvertorServiceImpl implements HtmlToPdfConvertorService{
    @Override
    public byte[] convertToPdf(String html) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            Document document = new W3CDom().fromJsoup(
                    Jsoup.parse(html));

            new PdfRendererBuilder()
                    .toStream(os)
                    .withW3cDocument(document, "src/main/resources/static/css")
                    .run();

            os.close();
        } catch(Exception e) {
            System.out.println(e);
        }

        return os.toByteArray();
    }
}
