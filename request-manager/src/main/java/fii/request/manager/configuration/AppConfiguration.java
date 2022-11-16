package fii.request.manager.configuration;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AppConfiguration {

    @Bean
    Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();

        try {
            File tmpFolder = LoadLibs.extractTessResources("win32-x86-64");
            System.setProperty("java.library.path", tmpFolder.getPath());

            tesseract.setDatapath("src/main/resources/3rdparty/tessdata");
            tesseract.setLanguage("ron");
            //tesseract.setDatapath(ClassLoader.getSystemResource("tessdata").toURI().toString());
            //tesseract.setOcrEngineMode(1);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("FATAL can not load tesseract OCR engine");
        }

        return tesseract;
    }
}
