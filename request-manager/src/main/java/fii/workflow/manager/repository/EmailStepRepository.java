package fii.workflow.manager.repository;

import fii.workflow.manager.domain.EmailStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailStepRepository extends JpaRepository<EmailStep, Long> {
    Optional<EmailStep> findByWorkflowStepId(Long workflowStepId);
}
