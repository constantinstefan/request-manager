package fii.request.manager.repository;

import fii.request.manager.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Set<Notification> findByCustomer_EmailAndIsOpenEqualsOrderByTimeDesc(String customer, Boolean isOpen);
}
