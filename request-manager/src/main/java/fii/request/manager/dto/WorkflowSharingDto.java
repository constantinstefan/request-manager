package fii.request.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fii.request.manager.domain.SharingType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WorkflowSharingDto {
    private Long id;

    private Long workflowId;

    private String sharingType;

    private Long groupId;
}
