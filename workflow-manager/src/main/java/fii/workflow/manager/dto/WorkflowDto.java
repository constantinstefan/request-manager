package fii.workflow.manager.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WorkflowDto {
    private Long id;
    private String name;
    private String description;

    private List<WorkflowStepDto> workflowSteps;

    private WorkflowSharingDto sharing;
}
