package fii.request.manager.service.preprocessing;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

public class ThresholdHandler implements PreprocessingHandler {
    @Override
    public Mat doPreprocessing(Mat inputImage) {
        System.out.println("threshold");
        Mat outputImage = new Mat();
        Imgproc.threshold(inputImage, outputImage, Imgproc.ADAPTIVE_THRESH_MEAN_C,255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
        return outputImage;
    }
}
