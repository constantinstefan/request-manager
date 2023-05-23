package fii.request.manager.service;

import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.WorkflowStepDto;
import fii.request.manager.exception.WorkflowNotFoundException;
import fii.request.manager.mapper.WorkflowStepMapper;
import fii.request.manager.repository.EditableHtmlRepository;
import fii.request.manager.repository.FormFieldRepository;
import fii.request.manager.repository.WorkflowRepository;
import fii.request.manager.repository.WorkflowStepRepository;
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

    private WorkflowStepMapper workflowStepMapper;


    @Autowired WorkflowStepService(WorkflowStepRepository workflowStepRepository,
                                   WorkflowRepository workflowRepository,
                                   FormFieldRepository formFieldRepository,
                                   WorkflowStepMapper workflowStepMapper,
                                   EditableHtmlRepository editableHtmlRepository) {
        this.workflowStepRepository = workflowStepRepository;
        this.workflowRepository = workflowRepository;
        this.formFieldRepository = formFieldRepository;
        this.workflowStepMapper = workflowStepMapper;
        this.editableHtmlRepository = editableHtmlRepository;
    }
    public WorkflowStep addWorkflowStep(Long workflowId, WorkflowStep workflowStep) {
        workflowStep.setWorkflow(workflowRepository.findById(workflowId).orElseThrow(WorkflowNotFoundException::new));
        return workflowStepRepository.save(workflowStep);
    }

    public List<WorkflowStepDto> getWorkflowSteps(Long workflowId) {
        return workflowStepRepository.findByWorkflowId(workflowId).stream()
                .map(workflowStepMapper::map).collect(Collectors.toList());
    }

    public List<WorkflowStepDto> getWorkflowStepsFetchingChildren(Long workflowId) {
        List<WorkflowStep> workflowSteps = workflowStepRepository.findByWorkflowId(workflowId);
        workflowSteps.stream()
                    .forEach(workflowStep -> {
                        switch (workflowStep.getStepType()) {
                            case "FORM_FIELDS": {
                                formFieldRepository.findByWorkflowStep(workflowStep).stream().forEach(formField -> {
                                    workflowStep.getFormFields().add(formField);
                                });
                            }
                            case "EDITABLE_HTML": {
                                editableHtmlRepository.findByWorkflowStepId(workflowStep.getWorkflowStepId())
                                        .ifPresent(editableHtml -> workflowStep.setEditableHtml(editableHtml));
                            }
                        }});
        return workflowSteps.stream().map(workflowStepMapper::map).collect(Collectors.toList());
    }

    public WorkflowStep getByWorkflowStepId(Long workflowStepId) {
        return workflowStepRepository.findById(workflowStepId).orElseThrow();
    }
}
