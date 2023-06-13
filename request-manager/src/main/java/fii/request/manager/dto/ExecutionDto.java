package fii.request.manager.dto;

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
public class ExecutionDto {
    private Long id;

    private Long stepNumber;
    private String message;
    private String status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Long workflowId;

    private Customer customer;
}
