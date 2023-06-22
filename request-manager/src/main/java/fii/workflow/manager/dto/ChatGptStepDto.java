package fii.workflow.manager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatGptStepDto {
    private Long id;

    Long workflowStepId;

    String outputVariable;

    String prompt;
}
