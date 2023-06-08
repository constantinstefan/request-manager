package fii.request.manager.controller;

import fii.request.manager.domain.Customer;
import fii.request.manager.dto.ChangePasswordDto;
import fii.request.manager.dto.CustomerDto;
import fii.request.manager.dto.CustomerGroupDto;
import fii.request.manager.dto.WorkflowDto;
import fii.request.manager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value="/{customerId}")
    @ResponseBody
    CustomerDto getById(@PathVariable Long customerId) {
        return customerService.getByIdFetchingGroups(customerId);
    }

    @GetMapping(value ="/{customerId}/groups")
    @ResponseBody
    List<CustomerGroupDto> getGroups(@PathVariable Long customerId){
        return customerService.getByIdFetchingGroups(customerId).getGroups();
    }

    @GetMapping
    @ResponseBody
    List<Customer> getAll() {
        return customerService.getAll().stream().toList();
    }

    @GetMapping(value = "/{customerId}/workflows")
    List<WorkflowDto> getByIdWithWorkflows(@PathVariable Long customerId) {
        return customerService.getByIdFetchingWorkflows(customerId).getWorkflows();
    }

    @PostMapping
    @ResponseBody
    CustomerDto addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @PatchMapping(value="/{customerId}/password")
    @ResponseBody
    CustomerDto changePassword(@PathVariable Long customerId, @RequestBody ChangePasswordDto changePasswordDto) {
        return customerService.changePassword(customerId, changePasswordDto);
    }
}
