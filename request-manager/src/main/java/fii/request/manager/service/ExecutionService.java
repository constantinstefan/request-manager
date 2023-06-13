package fii.request.manager.service;

import fii.request.manager.domain.Execution;
import fii.request.manager.dto.ExecutionDto;
import fii.request.manager.mapper.ExecutionMapper;
import fii.request.manager.repository.ExecutionRepository;
import fii.request.manager.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {

    private ExecutionRepository executionRepository;
    private WorkflowRepository workflowRepository;

    private NotificationService notificationService;

    @Autowired
    ExecutionService(ExecutionRepository executionRepository,
                     WorkflowRepository workflowRepository,
                     NotificationService notificationService) {
        this.executionRepository = executionRepository;
        this.workflowRepository = workflowRepository;
        this.notificationService = notificationService;
    }

    ExecutionDto saveExecution(Long workflowId, ExecutionDto execution) {
        Execution executionToSave = ExecutionMapper.map(execution);
        executionToSave.setWorkflow(workflowRepository.findById(workflowId).orElse(null));
        notificationService.sendExecutionNotification(executionToSave);
        return ExecutionMapper.map(executionRepository.save(executionToSave));
    }
}
