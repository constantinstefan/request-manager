package fii.request.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fii.request.manager.domain.CustomerGroup;
import fii.request.manager.domain.Role;
import fii.request.manager.domain.Workflow;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerDto {
    Long id;
    String email;
    String firstName;
    String lastName;
    String role;
    List<CustomerGroupDto> groups;

    List<WorkflowDto> workflows;
}
