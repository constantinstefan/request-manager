package fii.workflow.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EditableHtmlDto {

    Long workflowStepId;

    MultipartFile file;

    String uploadedEditedHtmlFileVariable;

    String pdfResultVariable;

    Boolean isRequired;
}
