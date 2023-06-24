package fii.workflow.manager.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Execution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stepNumber;
    private String message;
    private String status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
