package fii.workflow.manager.mapper;

import fii.workflow.manager.domain.WorkflowStep;
import fii.workflow.manager.dto.DocumentRequestDto;
import fii.workflow.manager.dto.EditableHtmlResponseDto;
import fii.workflow.manager.dto.FormFieldDto;
import fii.workflow.manager.dto.WorkflowStepDto;
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
        workflowStep.setStepNumber(workflowStepToChange.getStepNumber());
        workflowStep.setStepType(workflowStepToChange.getStepType());
        return workflowStep;
    }

    public WorkflowStepDto map(WorkflowStep workflowStep) {
        if(workflowStep == null) return null;
        return WorkflowStepDto.builder()
                .workflowStepId(workflowStep.getWorkflowStepId())
                .stepNumber(workflowStep.getStepNumber())
                .stepType(workflowStep.getStepType())
                .document(documentRequestMapper.map(workflowStep.getDocumentRequest()))
                .email(EmailStepMapper.map(workflowStep.getEmailStep()))
                .editableHtml(editableHtmlMapper.map(workflowStep.getEditableHtml()))
                .chatGptStep(ChatGptStepMapper.map(workflowStep.getChatGptStep()))
                .formFields(workflowStep.getFormFields().stream()
                        .map(FormFieldMapper::map).collect(Collectors.toList()))
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
