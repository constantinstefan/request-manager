package fii.request.manager.service.facade;

import fii.request.manager.domain.IdentityCardRequest;
import fii.request.manager.service.helper.convertor.HtmlToPdfConvertorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class PdfGeneratorFacade {
    private HtmlToPdfConvertorService htmlToPdfConvertorService;
    private TemplateEngine templateEngine;

    @Autowired
    public PdfGeneratorFacade(HtmlToPdfConvertorService htmlToPdfConvertorService,
                              TemplateEngine templateEngine) {
        this.htmlToPdfConvertorService = htmlToPdfConvertorService;
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(String templateName, IdentityCardRequest identityCardRequest) {

        final String templateContext = "request";

        Context context = new Context();
        context.setVariable(templateContext, identityCardRequest);

        String generatedHtml = templateEngine.process(templateName,context);

        //return htmlToPdfConvertorService.convertToPdf(generatedHtml);
        return generatedHtml.getBytes();
    }
}
