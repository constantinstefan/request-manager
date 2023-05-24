package fii.request.manager.service;

import fii.request.manager.domain.EditableHtml;
import fii.request.manager.dto.EditableHtmlDto;
import fii.request.manager.dto.EditableHtmlResponseDto;
import fii.request.manager.mapper.EditableHtmlMapper;
import fii.request.manager.repository.EditableHtmlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EditableHtmlService {
    private EditableHtmlMapper editableHtmlMapper;

    private EditableHtmlRepository editableHtmlRepository;

    @Autowired
    EditableHtmlService(EditableHtmlMapper editableHtmlMapper,
                        EditableHtmlRepository editableHtmlRepository) {
        this.editableHtmlMapper = editableHtmlMapper;
        this.editableHtmlRepository = editableHtmlRepository;
    }

    public EditableHtmlResponseDto addEditableHtml(EditableHtmlDto editableHtmlDto) {
        EditableHtml editableHtml = editableHtmlMapper.map(editableHtmlDto);
        return editableHtmlMapper.map(editableHtmlRepository.save(editableHtml));
    }

    public EditableHtmlResponseDto getByWorkflowStepId(Long workflowStepId) {
        return editableHtmlMapper.map(editableHtmlRepository.findByWorkflowStepId(workflowStepId).orElseThrow());
    }
}
