package fii.request.manager.service.preprocessing;

import org.opencv.core.*;
import org.opencv.dnn.TextDetectionModel;
import org.opencv.dnn.TextDetectionModel_EAST;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fii.request.manager.service.preprocessing.MatOfPointMapper.mapToMatOfPoint2f;

@Component
public class SkewHandler implements PreprocessingHandler{

    private ShapeDetector shapeDetector;

    @Autowired
    SkewHandler(ShapeDetector shapeDetector) {
        this.shapeDetector = shapeDetector;
    }

    @Override
    public Mat doPreprocessing(Mat inputImage) {
        System.out.println("skew");

        Mat greyImage = new GreyScaleHandler().doPreprocessing(inputImage);
        Mat normalizeImage = new NormalizeHandler().doPreprocessing(greyImage);
        Mat threseholdImage = new ThresholdHandler().doPreprocessing(normalizeImage);


        MatOfPoint2f corners = shapeDetector.getCorners(threseholdImage);
        MatOfPoint2f polygonCorners = shapeDetector.getApproximatePolygonCorners(corners);
        MatOfPoint2f frontPerspectiveCorners = shapeDetector.getFrontPerspectiveCorners(corners);
        Size frontPerspectiveSize = shapeDetector.getFrontPerspectiveSize(corners);

        Mat perspectiveTransformMatrix = Imgproc.getPerspectiveTransform(polygonCorners, frontPerspectiveCorners);

        printMatofPoint2f(polygonCorners);
        System.out.println("--");
        printMatofPoint2f(frontPerspectiveCorners);
        System.out.println("--");
        System.out.println(inputImage.size());
        System.out.println("--");
        System.out.println(frontPerspectiveSize);

        Mat outputImage = new Mat(inputImage.size(), inputImage.type());
        Imgproc.warpPerspective(inputImage, outputImage, perspectiveTransformMatrix,frontPerspectiveSize);

        outputImage =
                new AdaptiveThreseholdHandler().doPreprocessing(
                new NormalizeHandler().doPreprocessing(
                new GreyScaleHandler().doPreprocessing(outputImage)
        ));
        //Imgproc.cvtColor(outputImage, outputImage, Imgproc.COLOR_GRAY2RGB);
        //detectText(outputImage);

        return outputImage;
    }

    private static void printMatofPoint2f(MatOfPoint2f m) {
        Arrays.stream(m.toArray()).forEach(
                p -> System.out.println("point " + p.x + " " + p.y));
    }
    private static void detectText(Mat inputImage) {
        List<MatOfPoint> detections = new ArrayList<>();
        TextDetectionModel textDetectionModel = new TextDetectionModel_EAST("src/main/resources/3rdparty/opencv/frozen_east_text_detection.pb");
        Imgproc.resize(inputImage,inputImage, new Size(2720,1984));
        textDetectionModel.setInputSize(inputImage.size());
        textDetectionModel.detect(inputImage, detections);
        Arrays.stream(detections.toArray()).forEach(d -> System.out.println("detection " + d));
        for(var detection: detections) {
            Rect rect = Imgproc.boundingRect(detection);
            Imgproc.rectangle(inputImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
        }
    }
}
