package fii.request.manager.service.ocr;

import fii.request.manager.service.entityextractor.EntityExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@Primary
public class OcrServiceStub implements OcrService{

    @Autowired
    EntityExtractorService entityExtractorService;

    @Override
    public String processImage(BufferedImage bufferedImage) throws IOException {
        String text = "ROUMANIE\r\n542 NT\r\nROMANIA\r\nCARTE\r\nCARTE DE IDENTITATE\r\nD'IOENTITE\r\nNZ NR 223357\r\nCNP 1980521270023\r\nNume/Nom/Last name\r\nFTEFAN\r\nrenume/Prenom/First name\r\nCRISTIAN-CONSTANTIN\r\nCetåtenie/Nationalite/Nationality\r\nRomånä / ROU\r\nLoc nastere/Lieu de naissance/Piace of birth\r\nJud.NT Mun.Piatra Neamt\r\nDomiciliu/Adresse/Address '\r\nJud.NT Mun.Piatra Neamt\r\n09ca7\r\nSue Sex\r\nBd.Decebal nr.88 bl.E sc.Cet.3 ap.40\r\nValab1iitateVaiidtteiVaifdity\r\nEmiså de/Delivree par/lssued by\r\n07.10.22-21.052029\r\nSPCLEP Piatra Neamt\r\nNZ223357<OROU9805219M290521312700239\r\n";

        entityExtractorService.extractEntityFromText(text, "cnp");
        entityExtractorService.extractAllEntitiesFromTextInSameContext(text, "bd");
        entityExtractorService.extractEntityFromText(text, "Nume/Nom/Last name");
        entityExtractorService.extractEntityFromText(text,  "Prenume/Prenom/First name");
        entityExtractorService.extractEntityFromText(text,  "Jud");
        entityExtractorService.extractEntityFromText(text, "Mun");
        entityExtractorService.extractEntityFromText(text, "CARTE DE IDENTITATE");
        return text;
    }
}
