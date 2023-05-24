package fii.request.manager.repository;

import fii.request.manager.domain.EmailStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailStepRepository extends JpaRepository<EmailStep, Long> {
    Optional<EmailStep> findByWorkflowStepId(Long workflowStepId);
}
