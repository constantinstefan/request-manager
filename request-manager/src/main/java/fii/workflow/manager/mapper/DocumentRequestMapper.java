package fii.workflow.manager.mapper;

import fii.workflow.manager.domain.DocumentRequest;
import fii.workflow.manager.dto.DocumentRequestDto;
import org.springframework.stereotype.Service;

@Service
public class DocumentRequestMapper {

    public DocumentRequestDto map(DocumentRequest documentRequest) {
        if(documentRequest == null) return null;

        return DocumentRequestDto.builder()
                .id(documentRequest.getId())
                .description(documentRequest.getDescription())
                .uploadedFileVariable(documentRequest.getUploadedFileVariable())
                .isRequired(documentRequest.getIsRequired())
                .build();
    }

    public DocumentRequest mapForUpdate(DocumentRequest documentRequest, DocumentRequest documentRequestToChange) {
        documentRequest.setDescription(documentRequestToChange.getDescription());
        documentRequest.setIsRequired(documentRequestToChange.getIsRequired());
        documentRequest.setUploadedFileVariable(documentRequestToChange.getUploadedFileVariable());
        return documentRequest;
    }
}
