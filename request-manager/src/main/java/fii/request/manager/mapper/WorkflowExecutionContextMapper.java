package fii.request.manager.mapper;

import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.WorkflowExecutionContextDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

public class WorkflowExecutionContextMapper {
    public static WorkflowExecutionContext map(WorkflowExecutionContextDto workflowExecutionContextDto) {
        WorkflowExecutionContext workflowExecutionContext = WorkflowExecutionContext.builder()
                .fileContentByFileName(new HashMap<>())
                .variableValueByName(new HashMap<>())
                .workflowId(workflowExecutionContextDto.getWorkflowId())
                .build();
        workflowExecutionContextDto.getVariables().forEach(variable
                -> workflowExecutionContext.setVariable(variable.getName(), variable.getValue()));
        workflowExecutionContextDto.getFiles().forEach(file
                -> workflowExecutionContext.setFile(file.getName(), getBytes(file)));

        return workflowExecutionContext;
    }

    private static byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        }
        catch (IOException e) {}
        return null;
    }
}
