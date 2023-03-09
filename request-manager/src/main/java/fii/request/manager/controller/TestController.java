package fii.request.manager.controller;

import fii.request.manager.service.ocr.OcrService;
import fii.request.manager.service.preprocessing.PreprocessingHandlerChain;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.File;

@Controller
@RequestMapping("/api/test")
public class TestController {

    //delete this
    @Autowired
    Tesseract tesseract;
    @Autowired
    PreprocessingHandlerChain preprocessingHandlerChain;

    @Autowired
    OcrService ocrService;

    @GetMapping(value="/preprocess")
    public ResponseEntity<byte[]> testPreprocess(@RequestParam String image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        try {
            byte[] bytes = preprocessingHandlerChain
                    .doPreprocessing(FileUtils
                            .readFileToByteArray(
                                    new File("src/main/resources/" + image + ".jpg")));
            return new ResponseEntity<>(bytes,headers, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value="/ocr")
    public ResponseEntity<String> testOcr() {

        try {
            return new ResponseEntity<>(ocrService.processImage(null), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value="/ocr2")
    public ResponseEntity<String> testOcr2() {

        try {

            return new ResponseEntity<>(ocrService.processImage(null), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
