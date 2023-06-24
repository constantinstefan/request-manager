package fii.workflow.manager.repository;

import fii.workflow.manager.domain.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ExecutionRepository extends JpaRepository<Execution, Long> {

    Set<Execution> findByWorkflowIdOrderByStartTimeDesc(Long workflowId);
}
