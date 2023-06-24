package fii.workflow.manager.service;

import fii.workflow.manager.domain.Execution;
import fii.workflow.manager.dto.ExecutionDto;
import fii.workflow.manager.mapper.ExecutionMapper;
import fii.workflow.manager.repository.CustomerRepository;
import fii.workflow.manager.repository.ExecutionRepository;
import fii.workflow.manager.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutionService {

    private ExecutionRepository executionRepository;
    private WorkflowRepository workflowRepository;

    private CustomerRepository customerRepository;

    private WebsocketNotificationService websocketNotificationService;

    private NotificationService notificationService;

    @Autowired
    ExecutionService(ExecutionRepository executionRepository,
                     WorkflowRepository workflowRepository,
                     WebsocketNotificationService websocketNotificationService,
                     NotificationService notificationService,
                     CustomerRepository customerRepository) {
        this.executionRepository = executionRepository;
        this.workflowRepository = workflowRepository;
        this.customerRepository = customerRepository;
        this.websocketNotificationService = websocketNotificationService;
        this.notificationService = notificationService;
    }

    public ExecutionDto saveExecution(Long workflowId, Long customerId, ExecutionDto execution) {
        Execution executionToSave = ExecutionMapper.map(execution);
        executionToSave.setWorkflow(workflowRepository.findById(workflowId).orElse(null));
        executionToSave.setCustomer(customerRepository.findById(customerId).orElse(null));
        ExecutionDto executionDto = ExecutionMapper.map(executionRepository.save(executionToSave));
        websocketNotificationService.sendExecutionNotification(workflowId, executionDto);
        sendNotification(customerId, executionToSave);
        return executionDto;
    }

    public List<ExecutionDto> getExecutionsByWorkflowId(Long workflowId) {
        return executionRepository.findByWorkflowIdOrderByStartTimeDesc(workflowId).stream()
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
            case "IN_PROGRESS": {
                break;
            }
            default: {
                System.out.println("Warning wrong execution status" + execution);
            }
        }
    }
}
