package fii.request.manager.service.preprocessing;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PreprocessingHandlerChain {
    List<PreprocessingHandler> handlers;

    @Autowired
    public PreprocessingHandlerChain(List<PreprocessingHandler> handlers) {
        this.handlers=handlers;
    }

    public byte[] doPreprocessing(byte[] inputImage) {
        Mat imageMatrix = Imgcodecs.imdecode(new MatOfByte(inputImage), Imgcodecs.IMREAD_UNCHANGED);
        for(var handler: handlers) {
            imageMatrix = handler.doPreprocessing(imageMatrix);
        }
        //Imgproc.polylines(inputImage, , true, new Scalar(255,0,0),3);
        MatOfByte imageMatrixOfBytes = new MatOfByte();
        Imgcodecs.imencode(".jpg", imageMatrix, imageMatrixOfBytes);
        return imageMatrixOfBytes.toArray();
    }
}
