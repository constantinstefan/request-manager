package fii.request.manager.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DocumentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private boolean isOcrRequired;

    private String uploadedFileVariable;

    private String ocrResultVariable;

    private Boolean isRequired;
}
