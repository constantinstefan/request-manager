package fii.workflow.manager.repository;

import fii.workflow.manager.domain.FormField;
import fii.workflow.manager.domain.WorkflowStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FormFieldRepository extends JpaRepository<FormField, Long> {
    Set<FormField> findByWorkflowStep(WorkflowStep workflowStep);
}
