package fii.request.manager.service.helper.convertor;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import fii.request.manager.domain.EditableHtml;
import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.EditableHtmlResponseDto;
import fii.request.manager.dto.WorkflowExecutionContextDto;
import fii.request.manager.service.EditableHtmlService;
import fii.request.manager.service.StepRunnerService;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service("HtmlToPdfConvertorService")
public class HtmlToPdfConvertorServiceImpl implements HtmlToPdfConvertorService, StepRunnerService {
    private EditableHtmlService editableHtmlService;

    @Autowired
    HtmlToPdfConvertorServiceImpl(EditableHtmlService editableHtmlService) {
        this.editableHtmlService = editableHtmlService;
    }

    @Override
    public byte[] convertToPdf(byte[] htmlBytes) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            Document document = new W3CDom().fromJsoup(
                    Jsoup.parse(new ByteArrayInputStream(htmlBytes), null, ""));

            new PdfRendererBuilder()
                    .toStream(os)
                    .withW3cDocument(document, "src/main/resources/static/css")
                    .run();

            os.close();
        } catch(Exception e) {
            System.out.println(e);
        }

        return os.toByteArray();
    }

    @Override
    public void runServerStep(Long workflowStepId, WorkflowExecutionContext workflowExecutionContext) {
        EditableHtmlResponseDto editableHtml = editableHtmlService.getByWorkflowStepId(workflowStepId);
        String htmlKeyFile = editableHtml.getUploadedEditedHtmlFileVariable();
        String pdfKeyFile = editableHtml.getPdfResultVariable();
        byte[] htmlBytes = workflowExecutionContext.getFile(htmlKeyFile);
        workflowExecutionContext.setFile(pdfKeyFile, pdfKeyFile + ".pdf", convertToPdf(htmlBytes));
    }
}
