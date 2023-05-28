package fii.request.manager.repository;

import fii.request.manager.domain.Workflow;
import fii.request.manager.domain.WorkflowSharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    Set<Workflow> findBySharingId(Long id);
}
