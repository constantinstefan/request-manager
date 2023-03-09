package fii.request.manager.service.preprocessing;

import org.opencv.core.CvType;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;

public class MatOfPointMapper {
    public static MatOfPoint2f mapToMatOfPoint2f(MatOfPoint points) {
        MatOfPoint2f points2f = new MatOfPoint2f();
        points.convertTo(points2f, CvType.CV_32FC1);
        return points2f;
    }
}
