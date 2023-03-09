package fii.request.manager.service.preprocessing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;
import java.awt.image.BufferedImage;

//@Component
public class GreyScaleHandler implements PreprocessingHandler {
    @Override
    public Mat doPreprocessing(Mat inputImage) {
        System.out.println("grey scale");
        Mat outputImage = new Mat();
        Imgproc.cvtColor(inputImage, outputImage, Imgproc.COLOR_RGB2GRAY);
        Imgproc.blur(outputImage, outputImage, new Size(3,3));
        return outputImage;
    }
}
