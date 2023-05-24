package fii.request.manager.service.helper.convertor;

public interface HtmlToPdfConvertorService {
    byte[] convertToPdf(byte[] htmlBytes);
}
