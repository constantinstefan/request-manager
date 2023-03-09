package fii.request.manager.controller;

import fii.request.manager.dto.IdentityCardRequestDto;
import fii.request.manager.mapper.IdentityCardRequestMapper;
import fii.request.manager.service.IdentityCardRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/api/v1")
public class IdentityCardRequestController {
    IdentityCardRequestService identityCardRequestService;

    @Autowired
    IdentityCardRequestController(IdentityCardRequestService identityCardRequestService) {
        System.out.println("controller created");
        this.identityCardRequestService = identityCardRequestService;
    }

    @GetMapping(value = "/identity-card")
    public ResponseEntity<byte[]> getIdentityCard(@Valid @RequestBody IdentityCardRequestDto identityCardRequestDto) {
        byte[] content = identityCardRequestService.getPdf(
                IdentityCardRequestMapper.map(identityCardRequestDto)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

}
