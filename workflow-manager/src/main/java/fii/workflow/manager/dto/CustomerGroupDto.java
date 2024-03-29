package fii.workflow.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerGroupDto {
    Long id;
    String name;
    String description;
    List<CustomerDto> customers;
}
