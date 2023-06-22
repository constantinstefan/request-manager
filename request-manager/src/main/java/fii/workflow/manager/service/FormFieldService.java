package fii.workflow.manager.service;

import fii.workflow.manager.domain.FormField;
import fii.workflow.manager.domain.WorkflowStep;
import fii.workflow.manager.dto.FormFieldDto;
import fii.workflow.manager.mapper.FormFieldMapper;
import fii.workflow.manager.repository.FormFieldRepository;
import fii.workflow.manager.repository.WorkflowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormFieldService {
    private WorkflowStepService workflowStepService;

    private WorkflowStepRepository workflowStepRepository;

    private FormFieldRepository formFieldRepository;

    @Autowired
    FormFieldService(WorkflowStepService workflowStepService,
                     FormFieldRepository formFieldRepository,
                     WorkflowStepRepository workflowStepRepository) {
        this.workflowStepService = workflowStepService;
        this.formFieldRepository = formFieldRepository;
        this.workflowStepRepository = workflowStepRepository;
    }

    public FormField addFormField(Long workflowStepId, FormField formField) {
        formField.setWorkflowStep(workflowStepService.getByWorkflowStepId(workflowStepId));
        return formFieldRepository.save(formField);
    }

    public List<FormFieldDto> addFormFields(Long workflowStepId, List<FormFieldDto> formFields) {
        WorkflowStep workflowStep = workflowStepRepository.findById(workflowStepId).orElseThrow();
        workflowStep.setFormFields(formFields.stream()
                .map(formFieldDto -> FormFieldMapper.map(workflowStep, formFieldDto))
                .collect(Collectors.toSet()));
        workflowStepRepository.save(workflowStep);
        return formFields;
        /*return formFields.stream().map(formField -> {
            return addFormField(workflowStepId, formField);
        }).collect(Collectors.toList());*/
    }

    public List<FormField> updateFormFields(Long workflowStepId, List<FormField> formFields) {
        WorkflowStep workflowStep = workflowStepService.getByWorkflowStepId(workflowStepId);
        formFieldRepository.deleteAll(formFieldRepository.findByWorkflowStep(workflowStep));
        //return addFormFields(workflowStepId, formFields);
        return null;
    }
}
