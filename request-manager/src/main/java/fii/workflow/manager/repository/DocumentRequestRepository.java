package fii.workflow.manager.repository;

import fii.workflow.manager.domain.DocumentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
}
