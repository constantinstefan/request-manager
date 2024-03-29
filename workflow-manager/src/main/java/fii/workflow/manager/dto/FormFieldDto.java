package fii.workflow.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FormFieldDto {

    private Long id;

    private String label;
    private String name;
    private Boolean isRequired;
}
