package fii.request.manager.service.htttpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "ocr-space", url = "https://api.ocr.space")
public interface OcrSpaceClient {

    @PostMapping(value = "/Parse/Image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String parseImage(
            @RequestHeader("apikey") String apiKey,
            @RequestPart(value = "file", required = true) MultipartFile file,
            @RequestParam("scale") boolean scale,
            @RequestParam("OCREngine") int ocrEngine
    );
}
