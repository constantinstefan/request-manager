package fii.request.manager.mapper;

import fii.request.manager.domain.FormField;
import fii.request.manager.dto.FormFieldDto;

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

    public static FormField mapForUpdate(FormField formField, FormField formFieldToChange) {
        formField.setName(formFieldToChange.getName());
        formField.setLabel(formFieldToChange.getLabel());
        formField.setIsRequired(formFieldToChange.getIsRequired());
        return formField;
    }
}
