package fii.request.manager.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WorkflowStepDto {
    private Long workflowStepId;

    private Long workflowId;

    private String stepType;

    private Long stepNumber;

    private String stepName;

    private String stepDescription;

    private DocumentRequestDto document;

    private List<FormFieldDto> formFields;

    private EditableHtmlResponseDto editableHtml;
}
