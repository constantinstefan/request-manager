package fii.request.manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowExecutionContextDto {
    Long workflowId;
    List<NameValueDto> variables;
}
