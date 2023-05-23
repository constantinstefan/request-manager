package fii.request.manager.service;

import fii.request.manager.domain.SharingType;
import fii.request.manager.domain.WorkflowSharing;
import fii.request.manager.repository.WorkflowSharingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowSharingService {
    private WorkflowSharingRepository workflowSharingRepository;

    @Autowired
    WorkflowSharingService(WorkflowSharingRepository workflowSharingRepository) {
        this.workflowSharingRepository = workflowSharingRepository;
    }

    public WorkflowSharing addWorkflowSharing(Long workflowId, WorkflowSharing workflowSharing) {
        workflowSharing.setWorkflowId(workflowId);
        return workflowSharingRepository.save(workflowSharing);
    }

    public WorkflowSharing getWorkflowSharing(Long workflowId) {
        return workflowSharingRepository.findByWorkflowId(workflowId)
                .orElse(null);
    }

    public WorkflowSharing getWorkflowSharingByGroupId(Long groupId) {
        return workflowSharingRepository.findByGroupId(groupId).orElse(null);
    }

    public WorkflowSharing getWorkflowSharingBySharingType(SharingType sharingType) {
        return workflowSharingRepository.findBySharingType(sharingType).orElse(null);
    }
}
