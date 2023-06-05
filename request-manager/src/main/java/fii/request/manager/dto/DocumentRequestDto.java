package fii.request.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DocumentRequestDto {
    private Long id;

    private String description;

    private boolean isOcrRequired;

    private String uploadedFileVariable;

    private String ocrResultVariable;

    private Boolean isRequired;
}
