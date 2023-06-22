package fii.workflow.manager.mapper;

import fii.workflow.manager.domain.Workflow;
import fii.workflow.manager.dto.WorkflowDto;
import fii.workflow.manager.dto.WorkflowStepDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowMapper {

    private WorkflowStepMapper workflowStepMapper;

    @Autowired
    WorkflowMapper(WorkflowStepMapper workflowStepMapper) {
        this.workflowStepMapper = workflowStepMapper;
    }

    public WorkflowDto map(Workflow workflow, List<WorkflowStepDto> workflowSteps) {
        if(workflow == null) return null;
        WorkflowDto workflowDto = map(workflow);
        workflowDto.setWorkflowSteps(workflowSteps);
        return workflowDto;
    }

    public WorkflowDto map(Workflow workflow) {
        if(workflow == null) return null;
        return WorkflowDto.builder()
                .id(workflow.getId())
                .name(workflow.getName())
                .description(workflow.getDescription())
                .sharing(WorkflowSharingMapper.map(workflow.getSharing()))
                .build();
    }
}
