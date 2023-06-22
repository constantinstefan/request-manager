package fii.workflow.manager.service;

import fii.workflow.manager.domain.WorkflowExecutionContext;

public interface StepRunnerService {

    void runServerStep(Long workflowStepId, WorkflowExecutionContext workflowExecutionContext) throws Exception;
}
