package fii.workflow.manager.mapper;

import fii.workflow.manager.domain.Customer;
import fii.workflow.manager.domain.CustomerGroup;
import fii.workflow.manager.dto.CustomerDto;
import fii.workflow.manager.dto.WorkflowDto;

import java.util.List;
import java.util.Set;

public class CustomerMapper {

    public static CustomerDto map(Customer customer, Set<CustomerGroup> groups) {
        if(customer == null) return null;
        CustomerDto customerDto = map(customer);
        customerDto.setGroups(groups.stream().map(CustomerGroupMapper::map).toList());
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
                .build();
    }
}
