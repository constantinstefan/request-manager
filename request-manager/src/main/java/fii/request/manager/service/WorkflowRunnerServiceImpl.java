package fii.request.manager.service;
import fii.request.manager.dto.WorkflowExecutionContextDto;
import fii.request.manager.dto.WorkflowStepDto;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowRunnerServiceImpl implements ApplicationContextAware {

    private WorkflowStepService workflowStepService;

    private Map<String, StepRunnerService> stepRunnerServiceByStepType = new HashMap<>();

    private ApplicationContext applicationContext;

    @Autowired
    WorkflowRunnerServiceImpl(WorkflowStepService workflowStepService) {
        this.workflowStepService = workflowStepService;
        setUpStepRunners();
    }

    private void setUpStepRunners() {
        stepRunnerServiceByStepType.put("EDITABLE_HTML", null);//applicationContext.getBean());
    }
    public void runWorkflow(WorkflowExecutionContextDto workflowExecutionContextDto) {
        List<WorkflowStepDto> workflowSteps = workflowStepService.getWorkflowSteps(
                workflowExecutionContextDto.getWorkflowId());

        workflowSteps.forEach(workflowStep -> {
            StepRunnerService stepRunnerService = stepRunnerServiceByStepType.get(workflowStep.getStepType());
            if(stepRunnerService == null) {
                return;
            }
            stepRunnerService.runServerStep(workflowExecutionContextDto);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
