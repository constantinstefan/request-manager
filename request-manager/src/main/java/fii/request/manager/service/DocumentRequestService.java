package fii.request.manager.service;

import fii.request.manager.domain.DocumentRequest;
import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.DocumentRequestDto;
import fii.request.manager.mapper.DocumentRequestMapper;
import fii.request.manager.repository.DocumentRequestRepository;
import fii.request.manager.repository.WorkflowStepRepository;
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
        documentRequest = documentRequestRepository.save(documentRequest);
        WorkflowStep workflowStep = workflowStepRepository.findById(workflowStepId).orElseThrow();
        workflowStep.setDocumentRequest(documentRequest);
        workflowStepRepository.save(workflowStep);
        return documentRequestMapper.map(documentRequest);
    }
}
