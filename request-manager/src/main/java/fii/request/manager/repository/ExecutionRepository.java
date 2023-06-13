package fii.request.manager.repository;

import fii.request.manager.domain.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExecutionRepository extends JpaRepository<Execution, Long> {

    Optional<Execution> findByWorkflow(Long workflowId);
}
