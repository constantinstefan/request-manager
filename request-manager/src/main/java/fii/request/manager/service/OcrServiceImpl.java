package fii.request.manager.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class OcrServiceImpl implements OcrService {

    OcrServiceImpl() {

    }

    @Override
    public String processImage(BufferedImage bufferedImage) {
        return null;
    }
}
