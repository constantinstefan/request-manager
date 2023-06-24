package fii.workflow.manager.mapper;

import fii.workflow.manager.domain.WorkflowSharing;
import fii.workflow.manager.dto.WorkflowSharingDto;

public class WorkflowSharingMapper {
    public static WorkflowSharingDto map(WorkflowSharing workflowSharing) {
        if(workflowSharing == null) return null;
        return WorkflowSharingDto.builder()
                .groupId(workflowSharing.getGroupId())
                .id(workflowSharing.getId())
                .workflowId(workflowSharing.getWorkflowId())
                .sharingType(workflowSharing.getSharingType().toString())
                .build();
    }
}
