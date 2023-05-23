package fii.request.manager.service;

import fii.request.manager.domain.Customer;
import fii.request.manager.domain.CustomerGroup;
import fii.request.manager.domain.Workflow;
import fii.request.manager.dto.CustomerDto;
import fii.request.manager.dto.WorkflowDto;
import fii.request.manager.mapper.CustomerMapper;
import fii.request.manager.mapper.WorkflowMapper;
import fii.request.manager.repository.CustomerRepository;
import fii.request.manager.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    private GroupRepository groupRepository;

    private WorkflowService workflowService;

    private WorkflowMapper workflowMapper;

    @Autowired
    CustomerService(CustomerRepository customerRepository,
                    GroupRepository groupRepository,
                    WorkflowService workflowService,
                    WorkflowMapper workflowMapper) {
        this.customerRepository = customerRepository;
        this.groupRepository = groupRepository;
        this.workflowService = workflowService;
        this.workflowMapper = workflowMapper;
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public CustomerDto getByIdFetchingGroups(Long customerId) {
        Customer customer = getById(customerId);
        Set<CustomerGroup> groups = groupRepository.findByCustomers(customer);
        return CustomerMapper.map(customer, groups);
    }

    public CustomerDto getByIdFetchingWorkflows(Long customerId) {
        Customer customer = getById(customerId);
        Set<CustomerGroup> groups = groupRepository.findByCustomers(customer);
        List<WorkflowDto> workflows = new ArrayList<>();

        groups.stream().forEach(group -> {
            workflowService.getWorkflowsByGroupId(group.getGroupId()).stream()
                    .forEach(workflow -> {
                        workflows.add(workflowMapper.map(workflow));
                    });
        });

        workflowService.getPublicWorkflows().stream()
                .forEach(workflow -> {
                    workflows.add(workflowMapper.map(workflow));
                });
        return CustomerMapper.map(customer, workflows);
    }
    public Customer getById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(NoSuchElementException::new);
    }

    public Set<Customer> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customerRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Transactional
    public Customer addCustomer(Long groupId, Customer customerToAdd) {
        Customer customer = customerRepository.findById(customerToAdd.getCustomerId())
                .orElseThrow();
        CustomerGroup group = groupRepository.findById(groupId)
                .orElseThrow();
        customer.getCustomerGroups().add(group);
        return addCustomer(customer);
    }
}
