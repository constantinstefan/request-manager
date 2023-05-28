package fii.request.manager.mapper;

import fii.request.manager.domain.Workflow;
import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.WorkflowDto;
import fii.request.manager.dto.WorkflowStepDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
