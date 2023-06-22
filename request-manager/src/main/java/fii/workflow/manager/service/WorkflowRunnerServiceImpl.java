package fii.workflow.manager.service;

import fii.workflow.manager.domain.WorkflowExecutionContext;
import fii.workflow.manager.dto.ExecutionDto;
import fii.workflow.manager.dto.WorkflowStepDto;
import fii.workflow.manager.mapper.ExecutionMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class WorkflowRunnerServiceImpl implements ApplicationContextAware, WorkflowRunnerService {

    private WorkflowStepService workflowStepService;

    private Map<String, StepRunnerService> stepRunnerServiceByStepType = new HashMap<>();;

    private Map<Long, Future<?>> futureByExecutionId = new HashMap<>();

    private ApplicationContext applicationContext;

    private ExecutionService executionService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    WorkflowRunnerServiceImpl(WorkflowStepService workflowStepService,
                              ExecutionService executionService) {
        this.workflowStepService = workflowStepService;
        this.executionService = executionService;
    }

    @PostConstruct
    public void setupStepRunnerServices() {
        stepRunnerServiceByStepType.put("EDITABLE_HTML", applicationContext.getBean("HtmlToPdfConvertorService", StepRunnerService.class));
        stepRunnerServiceByStepType.put("EMAIL", applicationContext.getBean("EmailSenderService", StepRunnerService.class));
        stepRunnerServiceByStepType.put("CHATGPT", applicationContext.getBean("ChatGptStepRunnerService", StepRunnerService.class));
    }

    @Override
    @Transactional
    public void runWorkflow(Long workflowId, Long customerId, WorkflowExecutionContext workflowExecutionContext, ExecutionDto execution) {
        List<WorkflowStepDto> workflowSteps = workflowStepService.getWorkflowSteps(workflowId);

        try {
            for (val workflowStep : workflowSteps) {
                execution.setStepNumber(workflowStep.getStepNumber());
                execution = executionService.saveExecution(workflowId, customerId, execution);


                StepRunnerService stepRunnerService = stepRunnerServiceByStepType.get(workflowStep.getStepType());
                if (stepRunnerService == null) {
                    continue;
                }
                stepRunnerService.runServerStep(workflowStep.getWorkflowStepId(), workflowExecutionContext);
            }

            executionService.saveExecution(workflowId, customerId, ExecutionMapper.mapSuccess(execution));
            }
            catch(Exception e) {
                e.printStackTrace();
                executionService.saveExecution(workflowId, customerId, ExecutionMapper.mapFailure(execution, e.toString()));
            }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
