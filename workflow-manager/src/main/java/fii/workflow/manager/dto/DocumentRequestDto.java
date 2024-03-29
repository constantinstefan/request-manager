package fii.workflow.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DocumentRequestDto {
    private Long id;

    private String description;


    private String uploadedFileVariable;

    private Boolean isRequired;
}
