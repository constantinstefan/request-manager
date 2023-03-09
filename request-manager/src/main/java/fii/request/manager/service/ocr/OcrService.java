package fii.request.manager.service.ocr;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface OcrService {
    String processImage(BufferedImage bufferedImage) throws IOException;
}
