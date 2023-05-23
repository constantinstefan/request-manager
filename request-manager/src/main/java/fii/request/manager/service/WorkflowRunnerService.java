package fii.request.manager.service;

import fii.request.manager.dto.WorkflowExecutionContextDto;

public interface WorkflowRunnerService {
    void runWorkflow(WorkflowExecutionContextDto workflowExecutionContextDto);
}
