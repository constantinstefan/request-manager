package fii.request.manager.repository;

import fii.request.manager.domain.Customer;
import fii.request.manager.domain.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Set<Customer> findByCustomerGroups(CustomerGroup customerGroup);
}
