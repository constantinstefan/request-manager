package fii.request.manager.mapper;

import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.WorkflowExecutionContextDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

public class WorkflowExecutionContextMapper {
    public static WorkflowExecutionContext map(WorkflowExecutionContextDto workflowExecutionContextDto) {
        WorkflowExecutionContext workflowExecutionContext = WorkflowExecutionContext.builder()
                .fileContentByFileVariableName(new HashMap<>())
                .fileNameByFileVariableName(new HashMap<>())
                .variableValueByName(new HashMap<>())
                .workflowId(workflowExecutionContextDto.getWorkflowId())
                .build();
        workflowExecutionContextDto.getVariables().forEach(variable
                -> workflowExecutionContext.setVariable(variable.getName(), variable.getValue()));
        workflowExecutionContextDto.getFiles().forEach(file
                -> workflowExecutionContext.setFile(getBaseFileName(file.getOriginalFilename()), file.getOriginalFilename(), getBytes(file)));

        return workflowExecutionContext;
    }

    private static String getBaseFileName(String fileName) {
        int separatorIndex = fileName.lastIndexOf(".");
        if(separatorIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, separatorIndex);
    }

    private static byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        }
        catch (IOException e) {}
        return null;
    }
}
