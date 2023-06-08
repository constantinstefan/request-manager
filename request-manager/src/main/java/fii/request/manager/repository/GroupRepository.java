package fii.request.manager.repository;

import fii.request.manager.domain.Customer;
import fii.request.manager.domain.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends JpaRepository<CustomerGroup, Long> {
    Set<CustomerGroup> findByCustomers(Customer c);

    @Modifying
    @Transactional
    @Query(value = "DELETE from customer_to_group where customer_id=?1 and group_id=?2", nativeQuery = true)
    void deleteMemberByCustomerIdAndGroupId(Long customerId, Long groupId);
}
