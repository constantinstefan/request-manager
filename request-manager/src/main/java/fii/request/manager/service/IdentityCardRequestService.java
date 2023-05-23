package fii.request.manager.service;

import fii.request.manager.domain.Address;
import fii.request.manager.domain.County;
import fii.request.manager.dto.IdentityCardRequestDto;
import fii.request.manager.mapper.IdentityCardRequestMapper;
import fii.request.manager.service.entityextractor.EntityExtractorService;
import fii.request.manager.service.facade.PdfGeneratorFacade;
import fii.request.manager.service.ocr.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;

@Service
public class IdentityCardRequestService {
    private PdfGeneratorFacade pdfGeneratorFacade;
    private OcrService ocrService;
    
    private EntityExtractorService entityExtractorService;

    @Autowired
    public IdentityCardRequestService(PdfGeneratorFacade pdfGeneratorFacade,
                                      OcrService ocrService,
                                      EntityExtractorService entityExtractorService) {
        this.pdfGeneratorFacade = pdfGeneratorFacade;
        this.ocrService = ocrService;
        this.entityExtractorService = entityExtractorService;
    }

    public byte[] getPdf() throws Exception {
        final String text = ocrService.processImage(null);
        final HashMap<String, String> address = entityExtractorService.extractAllEntitiesFromTextInSameContext(text, "bd");
        final String personalNumericCode = entityExtractorService.extractEntityFromText(text,"cnp");
        final IdentityCardRequestDto identityCardRequestDto = IdentityCardRequestDto.builder()
                .personalNumericCode(entityExtractorService.extractEntityFromText(text,"cnp"))
                .firstName(entityExtractorService.extractEntityFromText(text,"Prenume/Prenom/First name"))
                .lastName(entityExtractorService.extractEntityFromText(text,"Nume/Nom/Last name"))
                .birthDate(LocalDate.parse("19" + personalNumericCode.substring(1,3)+"-"+personalNumericCode.substring(3,5)+"-"+personalNumericCode.substring(5,7)))
                .sex(personalNumericCode.substring(0,1).equals("1") ? "male" : "female")
                .currentAdress(Address.builder()
                        .streetName(address.get("bd"))
                        .streetNumber(address.get("nr"))
                        .buildingName(address.get("bl"))
                        .entrance(address.get("sc"))
                        .floorNumber(address.get("et"))
                        .apartment(address.get("ap"))
                        .townName(entityExtractorService.extractEntityFromText(text,"Mun"))
                        .countryName(County.getCountyNameByCode(entityExtractorService.extractEntityFromText(text, "Jud")))
                        .build())
                .reason("pierdere")
                .build();
        return pdfGeneratorFacade.generatePdf("buletin.html", IdentityCardRequestMapper.map(identityCardRequestDto));
    }
}
