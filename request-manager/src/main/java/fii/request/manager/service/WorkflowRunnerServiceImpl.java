package fii.request.manager.service;
import fii.request.manager.domain.Execution;
import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.ExecutionDto;
import fii.request.manager.dto.WorkflowExecutionContextDto;
import fii.request.manager.dto.WorkflowStepDto;
import fii.request.manager.mapper.ExecutionMapper;
import fii.request.manager.service.helper.convertor.HtmlToPdfConvertorService;
import fii.request.manager.service.helper.convertor.HtmlToPdfConvertorServiceImpl;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowRunnerServiceImpl implements ApplicationContextAware, WorkflowRunnerService {

    private WorkflowStepService workflowStepService;

    private Map<String, StepRunnerService> stepRunnerServiceByStepType = new HashMap<>();

    private ApplicationContext applicationContext;

    private ExecutionService executionService;

    @Autowired
    WorkflowRunnerServiceImpl(WorkflowStepService workflowStepService,
                              ExecutionService executionService) {
        this.workflowStepService = workflowStepService;
        this.executionService = executionService;
    }

    @PostConstruct
    private void setUpStepRunners() {
        stepRunnerServiceByStepType.put("EDITABLE_HTML", (StepRunnerService) applicationContext.getBean("HtmlToPdfConvertorService"));
        stepRunnerServiceByStepType.put("EMAIL", (StepRunnerService) applicationContext.getBean("EmailSenderService"));
        stepRunnerServiceByStepType.put("CHATGPT", (StepRunnerService) applicationContext.getBean("ChatGptStepRunnerService"));
    }

    @Override
    public void runWorkflow(Long workflowId, WorkflowExecutionContext workflowExecutionContext) {
        List<WorkflowStepDto> workflowSteps = workflowStepService.getWorkflowSteps(workflowId);

        ExecutionDto execution = ExecutionDto.builder()
                .startTime(LocalDateTime.now())
                .workflowId(workflowId)
                .status("IN_PROGRESS")
                .build();

        try {

            for (val workflowStep : workflowSteps) {
                execution.setStepNumber(workflowStep.getStepNumber());
                execution = executionService.saveExecution(workflowId, execution);


                StepRunnerService stepRunnerService = stepRunnerServiceByStepType.get(workflowStep.getStepType());
                if (stepRunnerService == null) {
                    continue;
                }
                stepRunnerService.runServerStep(workflowStep.getWorkflowStepId(), workflowExecutionContext);
            }

            executionService.saveExecution(workflowId, ExecutionMapper.mapSuccess(execution));
        }
        catch(Exception e) {
            executionService.saveExecution(workflowId, ExecutionMapper.mapFailure(execution, e.toString()));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
