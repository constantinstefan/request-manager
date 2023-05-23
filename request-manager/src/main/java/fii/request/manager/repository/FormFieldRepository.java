package fii.request.manager.repository;

import fii.request.manager.domain.FormField;
import fii.request.manager.domain.WorkflowStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FormFieldRepository extends JpaRepository<FormField, Long> {
    Set<FormField> findByWorkflowStep(WorkflowStep workflowStep);
}
