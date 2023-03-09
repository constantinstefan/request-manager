package fii.request.manager.service.preprocessing;

import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public interface PreprocessingHandler {
    Mat doPreprocessing(Mat inputImage);
}
