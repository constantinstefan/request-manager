package fii.request.manager.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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


    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="document_id")
    private DocumentRequest documentRequest;

    @OneToMany(mappedBy = "workflowStep", cascade = {CascadeType.ALL})
    private Set<FormField> formFields;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="editable_html_id")
    private EditableHtml editableHtml;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="email_step_id")
    private EmailStep emailStep;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="chatgpt_step_id")
    private ChatGptStep chatGptStep;
}
