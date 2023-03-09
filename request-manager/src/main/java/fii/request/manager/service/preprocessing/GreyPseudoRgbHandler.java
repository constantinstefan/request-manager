package fii.request.manager.service.preprocessing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class GreyPseudoRgbHandler implements PreprocessingHandler{
    @Override
    public Mat doPreprocessing(Mat inputImage) {
        Mat outputImage = new Mat();
        Imgproc.cvtColor(inputImage, outputImage, Imgproc.COLOR_RGB2Lab);
        List<Mat> channels = new ArrayList<>();
        Core.split(outputImage, channels);
        Imgproc.cvtColor(channels.get(0), outputImage, Imgproc.COLOR_GRAY2RGB);
        return outputImage;
    }
}
