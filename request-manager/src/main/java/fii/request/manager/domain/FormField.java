package fii.request.manager.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FormField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workflow_step_id")
    private WorkflowStep workflowStep;

    private String label;
    private String name;
    private Boolean isRequired;
}
