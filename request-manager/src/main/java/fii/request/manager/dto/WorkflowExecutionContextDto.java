package fii.request.manager.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class WorkflowExecutionContextDto {
    Long workflowId;
    List<NameValueDto> variables;

    List<MultipartFile> files;
}
