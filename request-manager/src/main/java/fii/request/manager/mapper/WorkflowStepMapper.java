package fii.request.manager.mapper;

import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.WorkflowStepDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WorkflowStepMapper {

    private EditableHtmlMapper editableHtmlMapper;
    private DocumentRequestMapper documentRequestMapper;

    @Autowired
    WorkflowStepMapper(EditableHtmlMapper editableHtmlMapper,
                       DocumentRequestMapper documentRequestMapper) {
        this.editableHtmlMapper = editableHtmlMapper;
        this.documentRequestMapper = documentRequestMapper;
    }



    public WorkflowStep map(WorkflowStepDto workflowStepDto) {
        WorkflowStep workflowStep = new WorkflowStep();
        return workflowStep;
    }

    public WorkflowStepDto map(WorkflowStep workflowStep) {
        if(workflowStep == null) return null;
        return WorkflowStepDto.builder()
                .workflowStepId(workflowStep.getWorkflowStepId())
                .stepName(workflowStep.getStepName())
                .stepNumber(workflowStep.getStepNumber())
                .stepDescription(workflowStep.getStepDescription())
                .stepType(workflowStep.getStepType())
                .formFields(workflowStep.getFormFields().stream()
                        .map(FormFieldMapper::map)
                        .collect(Collectors.toList()))
                .editableHtml(editableHtmlMapper.map(workflowStep.getEditableHtml()))
                .document(documentRequestMapper.map(workflowStep.getDocumentRequest()))
                .workflowId(workflowStep.getWorkflow().getId())
                .build();
    }
}
