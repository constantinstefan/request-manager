package fii.request.manager.service;

import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.WorkflowExecutionContextDto;

public interface WorkflowRunnerService {
    void runWorkflow(Long workflowId, WorkflowExecutionContext workflowExecutionContext);
}
