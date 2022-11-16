package fii.request.manager.service.preprocessing;

import org.springframework.stereotype.Component;
import java.awt.image.BufferedImage;

@Component
public class GreyScaleHandler implements PreprocessingHandler {
    @Override
    public BufferedImage doPreprocessing(BufferedImage inputImage) {
        System.out.println("grey scale");
        return inputImage;
    }
}
