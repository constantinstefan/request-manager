package fii.workflow.manager.service;

import fii.workflow.manager.domain.SharingType;
import fii.workflow.manager.domain.Workflow;
import fii.workflow.manager.domain.WorkflowSharing;
import fii.workflow.manager.repository.WorkflowRepository;
import fii.workflow.manager.repository.WorkflowSharingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowSharingService {
    private WorkflowSharingRepository workflowSharingRepository;
    private WorkflowRepository workflowRepository;

    @Autowired
    WorkflowSharingService(WorkflowSharingRepository workflowSharingRepository,
                           WorkflowRepository workflowRepository) {
        this.workflowSharingRepository = workflowSharingRepository;
        this.workflowRepository = workflowRepository;
    }

    public WorkflowSharing addWorkflowSharing(Long workflowId, WorkflowSharing workflowSharing) {
        if(workflowSharing.getSharingType().equals(SharingType.PUBLIC)) {
            WorkflowSharing publicSharing = getWorkflowSharingBySharingType(SharingType.PUBLIC);
            if(publicSharing != null) {
                Workflow workflow = workflowRepository.findById(workflowId).orElseThrow();
                workflow.setSharing(publicSharing);
                workflowRepository.save(workflow);
                return publicSharing;
            }
        }
        workflowSharing.setWorkflowId(workflowId);
        WorkflowSharing savedWorkflowSharing = workflowSharingRepository.save(workflowSharing);
        Workflow workflow = workflowRepository.findById(workflowId).orElseThrow();
        workflow.setSharing(savedWorkflowSharing);
        workflowRepository.save(workflow);
        return savedWorkflowSharing;
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
