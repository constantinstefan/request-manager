package fii.workflow.manager.service;

import fii.workflow.manager.domain.WorkflowStep;
import fii.workflow.manager.dto.WorkflowStepDto;
import fii.workflow.manager.exception.WorkflowNotFoundException;
import fii.workflow.manager.mapper.EditableHtmlMapper;
import fii.workflow.manager.mapper.WorkflowStepMapper;
import fii.workflow.manager.repository.EditableHtmlRepository;
import fii.workflow.manager.repository.FormFieldRepository;
import fii.workflow.manager.repository.WorkflowRepository;
import fii.workflow.manager.repository.WorkflowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowStepService {

    private WorkflowStepRepository workflowStepRepository;
    private WorkflowRepository workflowRepository;

    private FormFieldRepository formFieldRepository;

    private EditableHtmlRepository editableHtmlRepository;

    private EditableHtmlMapper editableHtmlMapper;

    private WorkflowStepMapper workflowStepMapper;


    @Autowired WorkflowStepService(WorkflowStepRepository workflowStepRepository,
                                   WorkflowRepository workflowRepository,
                                   FormFieldRepository formFieldRepository,
                                   WorkflowStepMapper workflowStepMapper,
                                   EditableHtmlRepository editableHtmlRepository,
                                   EditableHtmlMapper editableHtmlMapper) {
        this.workflowStepRepository = workflowStepRepository;
        this.workflowRepository = workflowRepository;
        this.formFieldRepository = formFieldRepository;
        this.workflowStepMapper = workflowStepMapper;
        this.editableHtmlMapper = editableHtmlMapper;
        this.editableHtmlRepository = editableHtmlRepository;
    }
    public WorkflowStep addWorkflowStep(Long workflowId, WorkflowStep workflowStep) {
        workflowStep.setWorkflow(workflowRepository.findById(workflowId).orElseThrow(WorkflowNotFoundException::new));
        return workflowStepRepository.save(workflowStep);
    }

    public WorkflowStepDto updateWorkflowStep(Long workflowStepId, WorkflowStep workflowStepToChangeWith) {
        WorkflowStep workflowStep = getByWorkflowStepId(workflowStepId);
        return workflowStepMapper.map(workflowStepRepository.save(workflowStepMapper.mapForUpdate(workflowStep, workflowStepToChangeWith)));
    }

    public List<WorkflowStepDto> getWorkflowSteps(Long workflowId) {
        return workflowStepRepository.findByWorkflowIdNotFetchingChildren(workflowId).stream()
                .map(workflowStep -> workflowStepMapper.map(workflowStep))
                .collect(Collectors.toList());
    }

    public List<WorkflowStepDto> getWorkflowStepsFetchingChildren(Long workflowId) {
        return workflowStepRepository.findByWorkflowId(workflowId).stream()
                .map(workflowStep -> workflowStepMapper.map(workflowStep))
                .collect(Collectors.toList());
    }

    public WorkflowStep getByWorkflowStepId(Long workflowStepId) {
        return workflowStepRepository.findById(workflowStepId).orElseThrow();
    }

    public void removeStep(Long workflowStepId) {
        workflowStepRepository.deleteById(workflowStepId);
    }
}
