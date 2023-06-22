package fii.workflow.manager.repository;

import fii.workflow.manager.domain.Customer;
import fii.workflow.manager.domain.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Set<Customer> findByCustomerGroups(CustomerGroup customerGroup);

    Optional<Customer> findByEmail(String email);
}
