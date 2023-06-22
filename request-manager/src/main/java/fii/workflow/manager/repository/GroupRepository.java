package fii.workflow.manager.repository;

import fii.workflow.manager.domain.Customer;
import fii.workflow.manager.domain.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface GroupRepository extends JpaRepository<CustomerGroup, Long> {
    Set<CustomerGroup> findByCustomers(Customer c);

    @Modifying
    @Transactional
    @Query(value = "DELETE from customer_to_group where customer_id=?1 and group_id=?2", nativeQuery = true)
    void deleteMemberByCustomerIdAndGroupId(Long customerId, Long groupId);
}
