package fii.workflow.manager.service;

import fii.workflow.manager.domain.EditableHtml;
import fii.workflow.manager.domain.WorkflowStep;
import fii.workflow.manager.dto.EditableHtmlDto;
import fii.workflow.manager.dto.EditableHtmlResponseDto;
import fii.workflow.manager.mapper.EditableHtmlMapper;
import fii.workflow.manager.repository.EditableHtmlRepository;
import fii.workflow.manager.repository.WorkflowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditableHtmlService {
    private EditableHtmlMapper editableHtmlMapper;

    private EditableHtmlRepository editableHtmlRepository;

    private WorkflowStepRepository workflowStepRepository;

    @Autowired
    EditableHtmlService(EditableHtmlMapper editableHtmlMapper,
                        EditableHtmlRepository editableHtmlRepository,
                        WorkflowStepRepository workflowStepRepository) {
        this.editableHtmlMapper = editableHtmlMapper;
        this.editableHtmlRepository = editableHtmlRepository;
        this.workflowStepRepository = workflowStepRepository;
    }

    public EditableHtmlResponseDto addEditableHtml(Long workflowStepId, EditableHtmlDto editableHtmlDto) {
        WorkflowStep workflowStep = workflowStepRepository.findById(workflowStepId).orElseThrow();
        EditableHtml editableHtml = editableHtmlMapper.map(editableHtmlDto);
        workflowStep.setEditableHtml(editableHtml);
        workflowStepRepository.save(workflowStep);
        return editableHtmlMapper.map(editableHtml);
    }

    public EditableHtmlResponseDto getByWorkflowStepId(Long workflowStepId) {
        return editableHtmlMapper.map(editableHtmlRepository.findByWorkflowStepId(workflowStepId).orElseThrow());
    }
}
