package fii.request.manager.service;

import fii.request.manager.domain.EmailStep;
import fii.request.manager.domain.WorkflowStep;
import fii.request.manager.dto.EmailStepDto;
import fii.request.manager.mapper.EmailStepMapper;
import fii.request.manager.repository.EmailStepRepository;
import fii.request.manager.repository.WorkflowStepRepository;
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

    public EmailStep addEmailStep(Long workflowStepId, EmailStepDto emailStepDto) {
        EmailStep emailStep = emailStepRepository.save(EmailStepMapper.map(workflowStepId, emailStepDto));
        WorkflowStep workflowStep = workflowStepRepository.findById(workflowStepId).orElseThrow();
        workflowStep.setEmailStep(emailStep);
        workflowStepRepository.save(workflowStep);
        return emailStep;
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
