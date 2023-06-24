package fii.workflow.manager.repository;

import fii.workflow.manager.domain.EditableHtml;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EditableHtmlRepository extends JpaRepository<EditableHtml, Long> {
    Optional<EditableHtml> findByWorkflowStepId(Long workflowStepId);
}
