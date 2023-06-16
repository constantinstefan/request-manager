package fii.request.manager.repository;

import fii.request.manager.domain.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ExecutionRepository extends JpaRepository<Execution, Long> {

    Set<Execution> findByWorkflowIdOrderByStartTimeDesc(Long workflowId);
}
