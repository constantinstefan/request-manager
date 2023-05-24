package fii.request.manager.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EditableHtml {
    @Id
    @GeneratedValue
    private Long id;

    private Long workflowStepId;

    private String path;

    private String uploadedEditedHtmlFileVariable;

    private String pdfResultVariable;
}
