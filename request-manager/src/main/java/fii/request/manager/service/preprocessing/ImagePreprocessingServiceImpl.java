package fii.request.manager.service.preprocessing;

import fii.request.manager.service.preprocessing.ImagePreprocessingService;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

@Service
public class ImagePreprocessingServiceImpl implements ImagePreprocessingService {

    @Override
    public byte[] getLessThan1MbResizedImage(String imagePathName) {
        Mat image = Imgcodecs.imread(imagePathName);
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, buffer);
        int imageSize = buffer.toArray().length;
        double rescaleFactor = 1000000.0 / (double) imageSize;
        Size size = new Size(image.width() * rescaleFactor, image.height() * rescaleFactor);

        Imgproc.resize(image, image, size);
        Imgcodecs.imencode(".jpg", image, buffer);
        byte[] resizedImage = buffer.toArray();
        System.out.printf("resized %s from %d to %d", imagePathName, imageSize, resizedImage.length);
        return resizedImage;
    }
}
