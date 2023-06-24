package fii.workflow.manager.repository;

import fii.workflow.manager.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Set<Notification> findByCustomer_EmailOrderByTimeDesc(String customer);
}
