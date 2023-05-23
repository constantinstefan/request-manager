package fii.request.manager.repository;

import fii.request.manager.domain.EditableHtml;
import fii.request.manager.domain.FormField;
import fii.request.manager.domain.WorkflowStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface EditableHtmlRepository extends JpaRepository<EditableHtml, Long> {
    Optional<EditableHtml> findByWorkflowStepId(Long workflowStepId);
}
