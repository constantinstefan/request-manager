package fii.request.manager.service;

import fii.request.manager.domain.SharingType;
import fii.request.manager.domain.Workflow;
import fii.request.manager.domain.WorkflowSharing;
import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.WorkflowDto;
import fii.request.manager.dto.WorkflowStepDto;
import fii.request.manager.exception.WorkflowNotFoundException;
import fii.request.manager.mapper.WorkflowMapper;
import fii.request.manager.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkflowService {
    private WorkflowRepository workflowRepository;
    private WorkflowStepService workflowStepService;

    private WorkflowSharingService workflowSharingService;

    private WorkflowMapper workflowMapper;

    @Autowired
    WorkflowService(WorkflowRepository workflowRepository,
                    WorkflowStepService workflowStepService,
                    WorkflowSharingService workflowSharingService,
                    WorkflowMapper workflowMapper) {
        this.workflowRepository = workflowRepository;
        this.workflowStepService = workflowStepService;
        this.workflowSharingService = workflowSharingService;
        this.workflowMapper = workflowMapper;
    }

    public WorkflowDto save(Workflow workflow) {
        return workflowMapper.map(workflowRepository.save(workflow));
    }


    public Workflow getWorkflowById(Long id) {
        return workflowRepository.findById(id)
                .orElseThrow(WorkflowNotFoundException::new);
    }
    public WorkflowDto getWorkflowByIdFetchingSteps(Long id) {
        System.out.println("getWorkflowById");
        Workflow workflow = getWorkflowById(id);
        List<WorkflowStepDto> workflowSteps = workflowStepService.getWorkflowStepsFetchingChildren(id);
        WorkflowSharing workflowSharing = workflowSharingService.getWorkflowSharing(id);
        workflow.setSharing(workflowSharing);
        return workflowMapper.map(workflow, workflowSteps);
    }

    public List<WorkflowDto> getAllWorkflows() {
        return workflowRepository.findAll().stream()
                .map(workflowMapper::map)
                .collect(Collectors.toList());
    }

    public Set<Workflow> getWorkflowsByGroupId(Long groupId) {
        WorkflowSharing sharing = workflowSharingService.getWorkflowSharingByGroupId(groupId);
        if(sharing == null) return Collections.emptySet();
        Set<Workflow> workflows = workflowRepository.findBySharingId(sharing.getId());
        workflows.forEach(workflow -> workflow.setSharing(sharing));
        return workflows;
    }

    public Set<Workflow> getPublicWorkflows() {
        WorkflowSharing publicSharing = workflowSharingService.getWorkflowSharingBySharingType(SharingType.PUBLIC);
        if(publicSharing == null) return Collections.emptySet();
        Set<Workflow> workflows = workflowRepository.findBySharingId(publicSharing.getId());
        workflows.forEach(workflow -> workflow.setSharing(publicSharing));
        return workflows;
    }

    public void assertContainsWorkflowStep(Long workflowId, Long workflowStepId) {
        workflowStepService.getWorkflowSteps(workflowId).stream()
                .filter(workflowStep -> workflowStep.getWorkflowStepId().equals(workflowStepId))
                .findFirst().orElseThrow();
    }
}
