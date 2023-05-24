package fii.request.manager.service;
import fii.request.manager.domain.WorkflowExecutionContext;
import fii.request.manager.dto.WorkflowExecutionContextDto;
import fii.request.manager.dto.WorkflowStepDto;
import fii.request.manager.service.helper.convertor.HtmlToPdfConvertorService;
import fii.request.manager.service.helper.convertor.HtmlToPdfConvertorServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowRunnerServiceImpl implements ApplicationContextAware, WorkflowRunnerService {

    private WorkflowStepService workflowStepService;

    private Map<String, StepRunnerService> stepRunnerServiceByStepType = new HashMap<>();

    private ApplicationContext applicationContext;

    @Autowired
    WorkflowRunnerServiceImpl(WorkflowStepService workflowStepService) {
        this.workflowStepService = workflowStepService;
    }

    @PostConstruct
    private void setUpStepRunners() {
        stepRunnerServiceByStepType.put("EDITABLE_HTML", (StepRunnerService) applicationContext.getBean("HtmlToPdfConvertorService"));
        stepRunnerServiceByStepType.put("EMAIL", (StepRunnerService) applicationContext.getBean("EmailSenderService"));
    }

    @Override
    public void runWorkflow(Long workflowId, WorkflowExecutionContext workflowExecutionContext) {
        List<WorkflowStepDto> workflowSteps = workflowStepService.getWorkflowSteps(workflowId);

        workflowSteps.forEach(workflowStep -> {
            StepRunnerService stepRunnerService = stepRunnerServiceByStepType.get(workflowStep.getStepType());
            if(stepRunnerService == null) {
                return;
            }
            stepRunnerService.runServerStep(workflowStep.getWorkflowStepId(), workflowExecutionContext);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
