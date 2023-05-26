package fii.request.manager.mapper;

import fii.request.manager.domain.Customer;
import fii.request.manager.domain.CustomerGroup;
import fii.request.manager.domain.Workflow;
import fii.request.manager.dto.CustomerDto;
import fii.request.manager.dto.WorkflowDto;

import java.util.List;
import java.util.Set;

public class CustomerMapper {

    public static CustomerDto map(Customer customer, Set<CustomerGroup> groups) {
        if(customer == null) return null;
        CustomerDto customerDto = map(customer);
        customerDto.setGroups(groups.stream().toList());
        return customerDto;
    }

    public static CustomerDto map(Customer customer, List<WorkflowDto> workflows) {
        if(customer == null) return null;
        CustomerDto customerDto = map(customer);
        customerDto.setWorkflows(workflows);
        return customerDto;
    }

    public static CustomerDto map(Customer customer) {
        if(customer == null) return null;
        return CustomerDto.builder()
                .id(customer.getCustomerId())
                .email(customer.getEmail())
                .role(customer.getRole().name())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }
}
