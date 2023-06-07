package fii.request.manager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailStepDto {
    private Long id;

    Long workflowStepId;

    String receiverEmail;
    String subject;
    String content;
    String attachments;
}
