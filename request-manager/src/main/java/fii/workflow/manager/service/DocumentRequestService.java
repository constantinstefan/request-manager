package fii.workflow.manager.service;

import fii.workflow.manager.domain.DocumentRequest;
import fii.workflow.manager.domain.WorkflowStep;
import fii.workflow.manager.dto.DocumentRequestDto;
import fii.workflow.manager.mapper.DocumentRequestMapper;
import fii.workflow.manager.repository.DocumentRequestRepository;
import fii.workflow.manager.repository.WorkflowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentRequestService {
    private DocumentRequestRepository documentRequestRepository;

    private DocumentRequestMapper documentRequestMapper;

    private WorkflowStepRepository workflowStepRepository;

    @Autowired
    DocumentRequestService(DocumentRequestRepository documentRequestRepository,
                           DocumentRequestMapper documentRequestMapper,
                           WorkflowStepRepository workflowStepRepository) {
        this.documentRequestMapper = documentRequestMapper;
        this.documentRequestRepository = documentRequestRepository;
        this.workflowStepRepository = workflowStepRepository;
    }

    public DocumentRequestDto addDocumentRequest(Long workflowStepId, DocumentRequest documentRequest) {
        WorkflowStep workflowStep = workflowStepRepository.findById(workflowStepId).orElseThrow();
        workflowStep.setDocumentRequest(documentRequest);
        workflowStepRepository.save(workflowStep);
        return documentRequestMapper.map(documentRequest);
    }

    public DocumentRequestDto updateDocumentRequest(Long workflowStepId, DocumentRequest documentRequestToChange) {
        WorkflowStep workflowStep = workflowStepRepository.findById(workflowStepId).orElseThrow();
        DocumentRequest documentRequest = documentRequestRepository.findById(workflowStep.getDocumentRequest().getId()).orElseThrow();
        return documentRequestMapper.map(documentRequestMapper.mapForUpdate(documentRequest, documentRequestToChange));
    }
}
