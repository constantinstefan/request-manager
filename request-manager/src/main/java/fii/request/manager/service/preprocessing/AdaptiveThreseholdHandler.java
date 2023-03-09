package fii.request.manager.service.preprocessing;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class AdaptiveThreseholdHandler implements PreprocessingHandler{
    @Override
    public Mat doPreprocessing(Mat inputImage) {
        System.out.println("adaptive threshold");
        Mat outputImage = new Mat();
        Imgproc.adaptiveThreshold(inputImage, outputImage,255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11,4);
        return outputImage;
    }
}
