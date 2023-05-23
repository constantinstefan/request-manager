package fii.request.manager.controller;

import fii.request.manager.domain.Customer;
import fii.request.manager.domain.CustomerGroup;
import fii.request.manager.dto.CustomerGroupDto;
import fii.request.manager.service.CustomerService;
import fii.request.manager.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    private GroupService groupService;
    private CustomerService customerService;

    @Autowired
    GroupController(GroupService groupService,
                    CustomerService customerService){
        this.groupService = groupService;
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseBody
    List<CustomerGroup> getAll() {
        return groupService.getAll();
    }

    @GetMapping(value ="/{groupId}")
    @ResponseBody
    CustomerGroupDto getByID(@PathVariable Long groupId) {
        return groupService.getByIdFetchingCustomers(groupId);
    }

    @PostMapping
    @ResponseBody
    CustomerGroup addGroup(@RequestBody CustomerGroup customerGroup) {
        return groupService.addGroup(customerGroup);
    }

    @PostMapping(value = "/{groupId}/members")
    @ResponseBody
    Customer addMember(@PathVariable Long groupId, @RequestBody Customer customer) {
        return customerService.addCustomer(groupId, customer);
    }
}
