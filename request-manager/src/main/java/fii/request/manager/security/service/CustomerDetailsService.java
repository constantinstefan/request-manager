package fii.request.manager.security.service;

import fii.request.manager.security.domain.CustomerDetails;
import fii.request.manager.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private CustomerService customerService;

    @Autowired
    CustomerDetailsService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return CustomerDetails.builder()
                .customer(customerService.getByEmail(username))
                .build();
    }
}
