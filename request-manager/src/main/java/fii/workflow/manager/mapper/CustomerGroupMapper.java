package fii.workflow.manager.mapper;

import fii.workflow.manager.domain.Customer;
import fii.workflow.manager.domain.CustomerGroup;
import fii.workflow.manager.dto.CustomerGroupDto;

import java.util.Set;

public class CustomerGroupMapper {

    public static CustomerGroupDto map(CustomerGroup customerGroup, Set<Customer> customerSet) {
        if(customerGroup == null) return null;
        CustomerGroupDto customerGroupDto = map(customerGroup);
        customerGroupDto.setCustomers(customerSet.stream().map(CustomerMapper::map).toList());
        return customerGroupDto;
    }

    public static CustomerGroupDto map(CustomerGroup customerGroup) {
        if(customerGroup == null) return null;
        return CustomerGroupDto.builder()
                .id(customerGroup.getGroupId())
                .name(customerGroup.getName())
                .description(customerGroup.getDescription())
                .build();
    }
}
