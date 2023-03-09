package fii.request.manager.service.ocr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fii.request.manager.service.ImagePreprocessingService;
import fii.request.manager.service.htttpclient.OcrSpaceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class OcrServiceImpl implements OcrService {
    OcrSpaceClient ocrSpaceClient;
    ImagePreprocessingService imagePreprocessingService;

    @Value("${ocr-space.api.key}")
    String apiKey;

    @Autowired
    OcrServiceImpl(OcrSpaceClient ocrSpaceClient,
                   ImagePreprocessingService imagePreprocessingService) {
        this.ocrSpaceClient = ocrSpaceClient;
        this.imagePreprocessingService = imagePreprocessingService;
    }

    @Override
    public String processImage(BufferedImage bufferedImage) throws IOException {
        String imagePathName = "src/main/resources/buletin3.jpg";
        byte[] resizedImage = imagePreprocessingService.getLessThan1MbResizedImage(imagePathName);

        MultipartFile multipartFile = new MockMultipartFile("file","buletin.jpg","image/jpeg", new ByteArrayInputStream(resizedImage));
        String jsonResponse = ocrSpaceClient.parseImage(apiKey, multipartFile,true,3);
        return getParsedTextFieldFromJsonResponse(jsonResponse);
    }

    private String getParsedTextFieldFromJsonResponse(String jsonResponse) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
            return jsonNode.get("ParsedResults").get(0).get("ParsedText").asText();
        }
        catch (IOException e) {
            System.out.println("warning unexpected json response format");
            return "";
        }
    }
}
