package fii.request.manager.service.preprocessing;

import java.awt.image.BufferedImage;

public interface PreprocessingHandler {
    BufferedImage doPreprocessing(BufferedImage inputImage);
}
