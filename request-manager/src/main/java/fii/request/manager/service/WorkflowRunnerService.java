package fii.request.manager.service;

import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.ExecutionDto;
import fii.request.manager.dto.WorkflowExecutionContextDto;

import java.util.concurrent.atomic.AtomicReference;

public interface WorkflowRunnerService {
    void runWorkflow(Long workflowId, Long customerId,WorkflowExecutionContext workflowExecutionContext, ExecutionDto execution);

}
