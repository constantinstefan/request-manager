package fii.request.manager.service.preprocessing;

public interface ImagePreprocessingService {

    byte[] getLessThan1MbResizedImage(String imagePathName);
}
