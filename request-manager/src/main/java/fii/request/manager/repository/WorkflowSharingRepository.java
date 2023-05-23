package fii.request.manager.repository;

import fii.request.manager.domain.SharingType;
import fii.request.manager.domain.WorkflowSharing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkflowSharingRepository extends JpaRepository<WorkflowSharing, Long> {
    Optional<WorkflowSharing> findByWorkflowId(Long workflowId);

    Optional<WorkflowSharing> findByGroupId(Long groupId);

    Optional<WorkflowSharing> findBySharingType(SharingType sharingType);
}
