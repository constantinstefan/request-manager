package fii.request.manager.service;

import fii.request.manager.domain.EditableHtml;
import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.EditableHtmlDto;
import fii.request.manager.dto.EditableHtmlResponseDto;
import fii.request.manager.mapper.EditableHtmlMapper;
import fii.request.manager.repository.EditableHtmlRepository;
import fii.request.manager.repository.WorkflowRepository;
import fii.request.manager.repository.WorkflowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
