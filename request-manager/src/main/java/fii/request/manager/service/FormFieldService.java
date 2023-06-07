package fii.request.manager.service;

import fii.request.manager.domain.FormField;
import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.repository.FormFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormFieldService {
    private WorkflowStepService workflowStepService;

    private FormFieldRepository formFieldRepository;

    @Autowired
    FormFieldService(WorkflowStepService workflowStepService,
                     FormFieldRepository formFieldRepository) {
        this.workflowStepService = workflowStepService;
        this.formFieldRepository = formFieldRepository;
    }

    public FormField addFormField(Long workflowStepId, FormField formField) {
        formField.setWorkflowStep(workflowStepService.getByWorkflowStepId(workflowStepId));
        return formFieldRepository.save(formField);
    }

    public List<FormField> addFormFields(Long workflowStepId, List<FormField> formFields) {
        return formFields.stream().map(formField -> {
            return addFormField(workflowStepId, formField);
        }).collect(Collectors.toList());
    }

    public List<FormField> updateFormFields(Long workflowStepId, List<FormField> formFields) {
        WorkflowStep workflowStep = workflowStepService.getByWorkflowStepId(workflowStepId);
        formFieldRepository.deleteAll(formFieldRepository.findByWorkflowStep(workflowStep));
        return addFormFields(workflowStepId, formFields);
    }
}
