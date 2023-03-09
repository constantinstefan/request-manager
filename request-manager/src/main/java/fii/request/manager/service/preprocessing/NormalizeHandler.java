package fii.request.manager.service.preprocessing;


import org.opencv.core.Core;
import org.opencv.core.Mat;

//@Component
public class NormalizeHandler implements PreprocessingHandler {
    @Override
    public Mat doPreprocessing(Mat inputImage) {
        System.out.println("normalize");
        Mat outputImage = new Mat();
        Core.normalize(inputImage, outputImage,0,255, Core.NORM_MINMAX);
        return outputImage;
    }
}
