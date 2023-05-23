package fii.request.manager.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class WorkflowStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workflowStepId;

    @ManyToOne
    @JoinColumn(name="workflow_id")
    private Workflow workflow;

    private String stepType;

    private Long stepNumber;

    private String stepName;

    private String stepDescription;

    @OneToOne
    @JoinColumn(name="document_id")
    private DocumentRequest documentRequest;

    @OneToMany(mappedBy = "workflowStep")
    private Set<FormField> formFields;

    @OneToOne
    @JoinColumn(name="editable_html_id")
    private EditableHtml editableHtml;
}
