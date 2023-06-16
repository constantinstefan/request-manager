package fii.request.manager.service;

import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.EditableHtmlDto;
import fii.request.manager.dto.FormFieldDto;
import fii.request.manager.dto.WorkflowStepDto;
import fii.request.manager.exception.WorkflowNotFoundException;
import fii.request.manager.mapper.EditableHtmlMapper;
import fii.request.manager.mapper.FormFieldMapper;
import fii.request.manager.mapper.WorkflowStepMapper;
import fii.request.manager.repository.EditableHtmlRepository;
import fii.request.manager.repository.FormFieldRepository;
import fii.request.manager.repository.WorkflowRepository;
import fii.request.manager.repository.WorkflowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
