package fii.request.manager.domain;

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

    private String fileResultLabel;

    private String ocrResultLabel;
}
