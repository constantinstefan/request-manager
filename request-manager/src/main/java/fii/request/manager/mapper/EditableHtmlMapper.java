package fii.request.manager.mapper;

import fii.request.manager.domain.EditableHtml;
import fii.request.manager.dto.EditableHtmlDto;
import fii.request.manager.dto.EditableHtmlResponseDto;
import fii.request.manager.service.LocalFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

@Component
public class EditableHtmlMapper {

    private LocalFileStorageService localFileStorageService;

    @Autowired EditableHtmlMapper(LocalFileStorageService localFileStorageService) {
        this.localFileStorageService = localFileStorageService;
    }

    public EditableHtml map(EditableHtmlDto editableHtmlDto) {
        if(editableHtmlDto == null) return null;
        EditableHtml editableHtml = new EditableHtml();
        editableHtml.setPath(localFileStorageService.store(editableHtmlDto.getFile()));
        editableHtml.setUploadedEditedHtmlFileVariable(editableHtmlDto.getUploadedEditedHtmlFileVariable());
        editableHtml.setWorkflowStepId(editableHtmlDto.getWorkflowStepId());
        editableHtml.setPdfResultVariable(editableHtmlDto.getPdfResultVariable());
        return editableHtml;
    }

    public EditableHtmlResponseDto map(EditableHtml editableHtml) {
        if(editableHtml == null) return null;
        return EditableHtmlResponseDto.builder()
                .content(HtmlUtils.htmlEscape(localFileStorageService.load(editableHtml.getPath())))
                .uploadedEditedHtmlFileVariable(editableHtml.getUploadedEditedHtmlFileVariable())
                .pdfResultVariable(editableHtml.getPdfResultVariable())
                .build();
    }
}