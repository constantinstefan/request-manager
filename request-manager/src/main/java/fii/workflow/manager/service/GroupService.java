package fii.workflow.manager.service;
import fii.workflow.manager.domain.Customer;
import fii.workflow.manager.domain.CustomerGroup;
import fii.workflow.manager.dto.CustomerGroupDto;
import fii.workflow.manager.mapper.CustomerGroupMapper;
import fii.workflow.manager.repository.CustomerRepository;
import fii.workflow.manager.repository.GroupRepository;
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

    public CustomerGroupDto addGroup(CustomerGroup customerGroup) {
        return CustomerGroupMapper.map(groupRepository.save(customerGroup));
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

    public void deleteCustomerFromGroup(Long groupId, Long customerId) {
        groupRepository.deleteMemberByCustomerIdAndGroupId(customerId, groupId);
    }
}
