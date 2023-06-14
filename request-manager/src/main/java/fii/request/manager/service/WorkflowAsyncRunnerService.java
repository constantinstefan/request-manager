package fii.request.manager.service;

import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.ExecutionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class WorkflowAsyncRunnerService {

    private WorkflowRunnerService workflowRunnerService;


    @Autowired
    WorkflowAsyncRunnerService(WorkflowRunnerService workflowRunnerService) {
        this.workflowRunnerService = workflowRunnerService;
    }

    @Async
    public void runWorkflowAsync(Long workflowId, Long customerId, WorkflowExecutionContext workflowExecutionContext, ExecutionDto execution) {
        workflowRunnerService.runWorkflow(workflowId, customerId, workflowExecutionContext, execution);
    }
}
