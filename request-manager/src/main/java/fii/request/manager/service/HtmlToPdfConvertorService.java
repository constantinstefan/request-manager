package fii.request.manager.service;

import fii.request.manager.dto.IdentityCardRequestDto;

public interface HtmlToPdfConvertorService {
    byte[] convert(IdentityCardRequestDto identityCardRequestDto);
}
