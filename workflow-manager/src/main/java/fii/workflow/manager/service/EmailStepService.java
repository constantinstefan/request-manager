package fii.workflow.manager.service;

import fii.workflow.manager.domain.EmailStep;
import fii.workflow.manager.domain.WorkflowStep;
import fii.workflow.manager.dto.EmailStepDto;
import fii.workflow.manager.mapper.EmailStepMapper;
import fii.workflow.manager.repository.EmailStepRepository;
import fii.workflow.manager.repository.WorkflowStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailStepService {

    private EmailStepRepository emailStepRepository;

    private WorkflowStepRepository workflowStepRepository;

    @Autowired
    EmailStepService(EmailStepRepository emailStepRepository,
                     WorkflowStepRepository workflowStepRepository) {
        this.emailStepRepository = emailStepRepository;
        this.workflowStepRepository = workflowStepRepository;
    }

    public EmailStepDto addEmailStep(Long workflowStepId, EmailStepDto emailStepDto) {
        WorkflowStep workflowStep = workflowStepRepository.findById(workflowStepId).orElseThrow();
        EmailStep emailStep = EmailStepMapper.map(workflowStepId, emailStepDto);
        workflowStep.setEmailStep(emailStep);
        workflowStepRepository.save(workflowStep);
        return EmailStepMapper.map(emailStep);
    }

    public EmailStepDto updateEmailStep(Long workflowStepId, EmailStep emailStepToChange) {
        WorkflowStep workflowStep = workflowStepRepository.findById(workflowStepId).orElseThrow();
        EmailStep emailStep = emailStepRepository.findById(workflowStep.getEmailStep().getId()).orElseThrow();
        return EmailStepMapper.map(EmailStepMapper.mapForUpdate(emailStep, emailStepToChange));
    }

    public EmailStep getByWorkflowStepId(Long workflowStepId) {
        return emailStepRepository.findByWorkflowStepId(workflowStepId).orElse(null);
    }
}
