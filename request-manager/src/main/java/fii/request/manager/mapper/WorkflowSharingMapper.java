package fii.request.manager.mapper;

import fii.request.manager.domain.WorkflowSharing;
import fii.request.manager.dto.WorkflowSharingDto;

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
