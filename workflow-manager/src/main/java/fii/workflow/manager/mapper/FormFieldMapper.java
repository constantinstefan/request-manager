package fii.workflow.manager.mapper;

import fii.workflow.manager.domain.FormField;
import fii.workflow.manager.domain.WorkflowStep;
import fii.workflow.manager.dto.FormFieldDto;

public class FormFieldMapper {

    public static FormFieldDto map(FormField formField) {
        if(formField == null) return null;
        return FormFieldDto.builder()
                .id(formField.getId())
                .label(formField.getLabel())
                .isRequired(formField.getIsRequired())
                .name(formField.getName())
                .build();
    }

    public static FormField map(WorkflowStep workflowStep, FormFieldDto formFieldDto) {
        if(formFieldDto == null) return null;
        FormField formField = new FormField();
        formField.setId(formFieldDto.getId());
        formField.setName(formFieldDto.getName());
        formField.setLabel(formFieldDto.getLabel());
        formField.setIsRequired(formFieldDto.getIsRequired());
        formField.setWorkflowStep(workflowStep);
        return formField;
    }

    public static FormField mapForUpdate(FormField formField, FormField formFieldToChange) {
        formField.setName(formFieldToChange.getName());
        formField.setLabel(formFieldToChange.getLabel());
        formField.setIsRequired(formFieldToChange.getIsRequired());
        return formField;
    }
}
