package fii.request.manager.service.preprocessing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MorphologicalHandler implements PreprocessingHandler{
    @Override
    public Mat doPreprocessing(Mat inputImage) {
        System.out.println("morphological");
        Mat outputImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Imgproc.erode(inputImage, outputImage, kernel);
        Imgproc.dilate(outputImage, outputImage, kernel);
        return outputImage;
    }
}
