package fii.request.manager.service.preprocessing;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ContrastEnhancementHandler implements PreprocessingHandler{
    @Override
    public Mat doPreprocessing(Mat inputImage) {
        System.out.println("contrast enhancement handler");
        Mat outputImage = new Mat();
        Imgproc.cvtColor(inputImage, inputImage, Imgproc.COLOR_RGB2GRAY);
        Imgproc.equalizeHist(inputImage, outputImage);
        Imgproc.cvtColor(inputImage, inputImage, Imgproc.COLOR_GRAY2RGB);
        return null;
    }
}
