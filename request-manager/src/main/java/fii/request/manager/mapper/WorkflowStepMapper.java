package fii.request.manager.mapper;

import fii.request.manager.domain.Workflow;
import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.DocumentRequestDto;
import fii.request.manager.dto.EditableHtmlResponseDto;
import fii.request.manager.dto.FormFieldDto;
import fii.request.manager.dto.WorkflowStepDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public WorkflowStep mapForUpdate(WorkflowStep workflowStep, WorkflowStep workflowStepToChange) {
        workflowStep.setStepName(workflowStepToChange.getStepName());
        workflowStep.setStepDescription(workflowStep.getStepDescription());
        workflowStep.setStepNumber(workflowStepToChange.getStepNumber());
        workflowStep.setStepType(workflowStepToChange.getStepType());
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
                .document(documentRequestMapper.map(workflowStep.getDocumentRequest()))
                .email(EmailStepMapper.map(workflowStep.getEmailStep()))
                .workflowId(workflowStep.getWorkflow().getId())
                .build();
    }

    public WorkflowStepDto map(WorkflowStep workflowStep, List<FormFieldDto> formFieldDtos) {
        WorkflowStepDto workflowStepDto = map(workflowStep);
        workflowStepDto.setFormFields(formFieldDtos);
        return workflowStepDto;
    }

    public WorkflowStepDto map(WorkflowStep workflowStep, EditableHtmlResponseDto editableHtmlDto) {
        WorkflowStepDto workflowStepDto = map(workflowStep);
        workflowStepDto.setEditableHtml(editableHtmlDto);
        return workflowStepDto;
    }

    public WorkflowStepDto map(WorkflowStep workflowStep, DocumentRequestDto documentRequestDto) {
        WorkflowStepDto workflowStepDto = map(workflowStep);
        workflowStepDto.setDocument(documentRequestDto);
        return workflowStepDto;
    }
}
