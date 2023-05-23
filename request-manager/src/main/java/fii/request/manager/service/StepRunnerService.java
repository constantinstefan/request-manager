package fii.request.manager.service;

import fii.request.manager.dto.WorkflowExecutionContextDto;

public interface StepRunnerService {

    void runServerStep(WorkflowExecutionContextDto workflowExecutionContextDto);
}
