package fii.request.manager.mapper;

import fii.request.manager.domain.EmailStep;
import fii.request.manager.dto.EmailStepDto;

public class EmailStepMapper {
    public static EmailStep map(Long workflowStepId, EmailStepDto emailStepDto) {
        EmailStep emailStep = new EmailStep();
        emailStep.setWorkflowStepId(workflowStepId);
        emailStep.setReceiverEmail(emailStepDto.getReceiverEmail());
        emailStep.setId(emailStepDto.getId());
        emailStep.setSubject(emailStepDto.getSubject());
        emailStep.setAttachements(emailStepDto.getAttachements());
        emailStep.setContent(emailStepDto.getContent());
        return emailStep;
    }
}
