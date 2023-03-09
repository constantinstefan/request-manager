package fii.request.manager.service.preprocessing;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ShapeDetector {

    public MatOfPoint2f getCorners(Mat inputImage) {
        List<MatOfPoint> corners = new ArrayList<>();
        Imgproc.findContours(inputImage,corners,new Mat(), Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_TC89_L1);

        return corners.stream()
                .map(MatOfPointMapper::mapToMatOfPoint2f)
                .max(Comparator.comparing(Imgproc::contourArea))
                .orElse(null);
    }

    public Double getCornersPerimeter(MatOfPoint2f corners) {
        return Imgproc.arcLength(corners, true);
    }

    public MatOfPoint2f getApproximatePolygonCorners(MatOfPoint2f corners) {
        MatOfPoint2f polygonCorners = new MatOfPoint2f();
        Imgproc.approxPolyDP(corners,polygonCorners, 0.015*getCornersPerimeter(corners),true);

        return polygonCorners;
    }

    public MatOfPoint2f getFrontPerspectiveCorners(MatOfPoint2f corners) {
        double maxX = corners.toList().stream()
                .mapToDouble(point -> point.x).max().getAsDouble();

        double minX = corners.toList().stream()
                .mapToDouble(point -> point.x).min().getAsDouble();

        double maxY = corners.toList().stream()
                .mapToDouble(point -> point.y).max().getAsDouble();

        double minY = corners.toList().stream().
                mapToDouble(point -> point.y).min().getAsDouble();

        double maxWidth = maxX - minX;
        double maxHeight = maxY -minY;

        return new MatOfPoint2f(
                new Point(0,0),
                new Point(0, maxHeight),
                new Point(maxWidth, maxHeight),
                new Point(maxWidth, 0));
    }

    public Size getFrontPerspectiveSize(MatOfPoint2f corners) {
        double maxX = corners.toList().stream()
                .mapToDouble(point -> point.x).max().getAsDouble();

        double minX = corners.toList().stream()
                .mapToDouble(point -> point.x).min().getAsDouble();

        double maxY = corners.toList().stream()
                .mapToDouble(point -> point.y).max().getAsDouble();

        double minY = corners.toList().stream().
                mapToDouble(point -> point.y).min().getAsDouble();

        double maxWidth = maxX - minX;
        double maxHeight = maxY -minY;

        return new Size(maxWidth, maxHeight);
    }



}
