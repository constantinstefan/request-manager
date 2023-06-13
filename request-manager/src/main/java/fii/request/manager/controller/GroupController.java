package fii.request.manager.controller;

import fii.request.manager.domain.Customer;
import fii.request.manager.domain.CustomerGroup;
import fii.request.manager.dto.CustomerDto;
import fii.request.manager.dto.CustomerGroupDto;
import fii.request.manager.service.CustomerService;
import fii.request.manager.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    CustomerGroupDto addGroup(@RequestBody CustomerGroup customerGroup) {
        return groupService.addGroup(customerGroup);
    }

    @GetMapping(value = "/{groupId}/members")
    @ResponseBody
    List<CustomerDto> getMembersByID(@PathVariable Long groupId) {
        return groupService.getByIdFetchingCustomers(groupId).getCustomers();
    }

    @PostMapping(value = "/{groupId}/members")
    @ResponseBody
    CustomerDto addMember(@PathVariable Long groupId, @RequestBody CustomerDto customer) {
        return customerService.addCustomer(groupId, customer);
    }

    @DeleteMapping(value = "/{groupId}/members/{customerId}")
    @ResponseBody
    ResponseEntity deleteMember(@PathVariable Long groupId,
                                @PathVariable Long customerId) {
        groupService.deleteCustomerFromGroup(groupId, customerId);
        return ResponseEntity.ok().build();
    }
}
