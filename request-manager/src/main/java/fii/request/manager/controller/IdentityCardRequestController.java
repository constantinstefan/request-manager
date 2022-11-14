package fii.request.manager.controller;

import fii.request.manager.service.HtmlToPdfConvertorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IdentityCardRequestController {
    HtmlToPdfConvertorService htmlToPdfConvertorService;

    @Autowired
    IdentityCardRequestController(HtmlToPdfConvertorService htmlToPdfConvertorService) {
        System.out.println("controller created");
        this.htmlToPdfConvertorService = htmlToPdfConvertorService;
    }

    @GetMapping(value = "/identity-card")
    public ResponseEntity<byte[]> getIdentityCard() {
        byte[] content = htmlToPdfConvertorService.convert(null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

}
