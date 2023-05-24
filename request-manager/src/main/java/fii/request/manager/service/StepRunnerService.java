package fii.request.manager.service;

import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.WorkflowExecutionContextDto;

public interface StepRunnerService {

    void runServerStep(Long workflowStepId, WorkflowExecutionContext workflowExecutionContext);
}
