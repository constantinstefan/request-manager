package fii.workflow.manager.service;

import fii.workflow.manager.domain.WorkflowExecutionContext;
import fii.workflow.manager.dto.ExecutionDto;

public interface WorkflowRunnerService {
    void runWorkflow(Long workflowId, Long customerId, WorkflowExecutionContext workflowExecutionContext, ExecutionDto execution);

}
