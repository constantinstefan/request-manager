package fii.workflow.manager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditableHtmlResponseDto {
    String content;

    private String uploadedEditedHtmlFileVariable;

    private String pdfResultVariable;

    private Boolean isRequired;
}
