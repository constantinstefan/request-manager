package fii.request.manager.service;
import fii.request.manager.domain.Customer;
import fii.request.manager.domain.CustomerGroup;
import fii.request.manager.dto.CustomerGroupDto;
import fii.request.manager.mapper.CustomerGroupMapper;
import fii.request.manager.repository.CustomerRepository;
import fii.request.manager.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private CustomerRepository customerRepository;

    @Autowired
    GroupService(GroupRepository groupRepository,
                 CustomerRepository customerRepository) {
        this.groupRepository = groupRepository;
        this.customerRepository = customerRepository;
    }

    public CustomerGroup addGroup(CustomerGroup customerGroup) {
        return groupRepository.save(customerGroup);
    }

    public CustomerGroupDto getByIdFetchingCustomers(Long groupId) {
        CustomerGroup customerGroup = getById(groupId);
        Set<Customer> customers = customerRepository.findByCustomerGroups(customerGroup);
        return CustomerGroupMapper.map(customerGroup, customers);
    }

    public CustomerGroup getById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow();
    }

    public List<CustomerGroup> getAll() {
        return groupRepository.findAll();
    }
}