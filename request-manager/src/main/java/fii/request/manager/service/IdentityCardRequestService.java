package fii.request.manager.service;

import fii.request.manager.domain.IdentityCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.validation.Valid;

@Service
public class IdentityCardRequestService {
    private HtmlToPdfConvertorService htmlToPdfConvertorService;
    private TemplateEngine templateEngine;

    @Autowired
    public IdentityCardRequestService(HtmlToPdfConvertorService htmlToPdfConvertorService,
                                      TemplateEngine templateEngine) {
        this.htmlToPdfConvertorService = htmlToPdfConvertorService;
        this.templateEngine = templateEngine;
    }

    public byte[] getPdf(@Valid IdentityCardRequest identityCardRequest) {
        final String templateName = "buletin";

        Context context = new Context();
        context.setVariable("request", identityCardRequest);

        String generatedHtml = templateEngine.process(templateName,context);

        return htmlToPdfConvertorService.convertToPdf(generatedHtml);
    }
}
