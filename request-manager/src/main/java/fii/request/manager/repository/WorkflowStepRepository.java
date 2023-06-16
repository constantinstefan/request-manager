package fii.request.manager.repository;

import fii.request.manager.domain.WorkflowStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkflowStepRepository extends JpaRepository<WorkflowStep, Long> {
    List<WorkflowStep> findByWorkflowId(Long workflowId);

    @Query("SELECT ws FROM WorkflowStep ws LEFT JOIN ws.workflow w WHERE w.id=:workflowId")
    List<WorkflowStep> findByWorkflowIdNotFetchingChildren(@Param(value="workflowId") Long workflowId);
}
