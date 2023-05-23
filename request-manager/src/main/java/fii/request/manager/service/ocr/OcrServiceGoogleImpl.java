package fii.request.manager.service.ocr;

import com.google.cloud.vision.v1.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.protobuf.ByteString;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Service
//@Primary
public class OcrServiceGoogleImpl implements OcrService{

    private ImageAnnotatorClient client = null;
    @Override
    public String processImage(BufferedImage bufferedImage) throws IOException {
        detectText("src/main/resources/certificat-nastere-2.jpg");
        return null;
    }

    OcrServiceGoogleImpl() {
        try{
            client = ImageAnnotatorClient.create();
        } catch(Exception e) {
            client = null;
        }
    }

    public void detectText(String filePath) throws IOException {
        ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        // client.batchAnnotateImages(requests);
        AnnotateImageResponse response = client.batchAnnotateImages(Collections.singletonList(request))
                    .getResponsesList().get(0);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<List<AnnotateImageResponse>>(){}.getType();
        String json = gson.toJson(response, listType);

        try {
                FileWriter writer = new FileWriter(new File("src/main/resources/google-cloud.json"));
                writer.write(json);
                writer.close();
        } catch (IOException e) {
                e.printStackTrace();
        }

        if (response.hasError()) {
                    System.out.format("Error: %s%n", response.getError().getMessage());
                    return;
        }
        response.getFullTextAnnotation();
        for (EntityAnnotation annotation : response.getTextAnnotationsList()) {
                    System.out.format("Text: %s%n", annotation.getDescription());
                    System.out.format("Position : %s%n", annotation.getBoundingPoly());
        }
    }
}
