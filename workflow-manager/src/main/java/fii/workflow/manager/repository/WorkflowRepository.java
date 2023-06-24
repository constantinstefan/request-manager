package fii.workflow.manager.repository;

import fii.workflow.manager.domain.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    Set<Workflow> findBySharingId(Long id);
}
