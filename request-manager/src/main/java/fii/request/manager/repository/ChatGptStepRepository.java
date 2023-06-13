package fii.request.manager.repository;

import fii.request.manager.domain.ChatGptStep;
import fii.request.manager.domain.EmailStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatGptStepRepository  extends JpaRepository<ChatGptStep, Long> {

    Optional<ChatGptStep> findByWorkflowStepId(Long workflowStepId);
}
