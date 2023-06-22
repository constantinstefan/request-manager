package fii.workflow.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExecutionDto {
    private Long id;

    private Long stepNumber;
    private String message;
    private String status;

    private Long startTime;
    private Long endTime;

    private Long workflowId;

    private CustomerDto customer;
}
