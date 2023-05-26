package fii.request.manager.configuration;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;
import nu.pattern.OpenCV;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;

@Configuration
public class AppConfiguration {

    @Bean
    void openCV() {
        OpenCV.loadLocally();
    }

    @Bean
    Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();

        try {
            File tmpFolder = LoadLibs.extractTessResources("win32-x86-64");
            System.out.println(tmpFolder.getPath());
            System.setProperty("java.library.path", tmpFolder.getPath());
            tesseract.setDatapath("src/main/resources/3rdparty/tessdata");
            //tesseract.setLanguage("ron");
            tesseract.setLanguage("eng");
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("FATAL can not load tesseract OCR engine");
        }

        return tesseract;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
