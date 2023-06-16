package fii.request.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fii.request.manager.domain.Customer;
import fii.request.manager.domain.Workflow;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


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
