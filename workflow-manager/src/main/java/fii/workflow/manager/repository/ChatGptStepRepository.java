package fii.workflow.manager.repository;

import fii.workflow.manager.domain.ChatGptStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatGptStepRepository  extends JpaRepository<ChatGptStep, Long> {

    Optional<ChatGptStep> findByWorkflowStepId(Long workflowStepId);
}
