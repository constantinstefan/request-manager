package fii.request.manager.service;

import fii.request.manager.domain.Execution;
import fii.request.manager.dto.ExecutionDto;
import fii.request.manager.mapper.ExecutionMapper;
import fii.request.manager.repository.ExecutionRepository;
import fii.request.manager.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutionService {

    private ExecutionRepository executionRepository;
    private WorkflowRepository workflowRepository;

    private WebsocketNotificationService websocketNotificationService;

    private NotificationService notificationService;

    @Autowired
    ExecutionService(ExecutionRepository executionRepository,
                     WorkflowRepository workflowRepository,
                     WebsocketNotificationService websocketNotificationService,
                     NotificationService notificationService) {
        this.executionRepository = executionRepository;
        this.workflowRepository = workflowRepository;
        this.websocketNotificationService = websocketNotificationService;
        this.notificationService = notificationService;
    }

    public ExecutionDto saveExecution(Long workflowId, Long customerId, ExecutionDto execution) {
        Execution executionToSave = ExecutionMapper.map(execution);
        executionToSave.setWorkflow(workflowRepository.findById(workflowId).orElse(null));
        websocketNotificationService.sendExecutionNotification(executionToSave);
        sendNotification(customerId, executionToSave);
        return ExecutionMapper.map(executionRepository.save(executionToSave));
    }

    public List<ExecutionDto> getExecutionsByWorkflowId(Long workflowId) {
        return executionRepository.findByWorkflowId(workflowId).stream()
                .map(ExecutionMapper::map).toList();
    }

    private void sendNotification(Long customerId, Execution execution) {
        switch(execution.getStatus()) {
            case "SUCCESS" : {
                notificationService.sendNotification(customerId,"Sucessfully completed " + execution.getWorkflow().getName());
                break;
            }
            case "FAILURE": {
                notificationService.sendNotification(customerId,"Failed to complete " + execution.getWorkflow().getName());
                break;
            }
            default: {
                System.out.println("Warning wrong execution status" + execution);
            }
        }
    }
}
