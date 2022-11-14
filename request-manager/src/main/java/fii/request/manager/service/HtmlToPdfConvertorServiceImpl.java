package fii.request.manager.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import fii.request.manager.dto.IdentityCardRequestDto;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class HtmlToPdfConvertorServiceImpl implements HtmlToPdfConvertorService{
    @Override
    public byte[] convert(IdentityCardRequestDto identityCardRequestDto) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            Document document = new W3CDom().fromJsoup(
                    Jsoup.parse(new File("src/main/resources/templates/buletin.html"), "UTF-8"));

            new PdfRendererBuilder()
                    .toStream(os)
                    .withW3cDocument(document, "src/main/resources/templates")
                    .run();

            os.close();
        } catch(Exception e) {
            System.out.println(e);
        }

        return os.toByteArray();
    }
}
