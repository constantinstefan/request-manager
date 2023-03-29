package fii.request.manager.service;

public interface ImagePreprocessingService {

    byte[] getLessThan1MbResizedImage(String imagePathName);
}
