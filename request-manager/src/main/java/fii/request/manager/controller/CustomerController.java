package fii.request.manager.controller;

import fii.request.manager.domain.Customer;
import fii.request.manager.dto.CustomerDto;
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

    @GetMapping
    @ResponseBody
    List<Customer> getAll() {
        return customerService.getAll().stream().toList();
    }

    @GetMapping(value = "/{customerId}/workflows")
    CustomerDto getByIdWithWorkflows(@PathVariable Long customerId) {
        return customerService.getByIdFetchingWorkflows(customerId);
    }

    @PostMapping
    @ResponseBody
    Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }
}
