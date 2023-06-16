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
        emailStep.setAttachments(emailStepDto.getAttachments());
        emailStep.setContent(emailStepDto.getContent());
        return emailStep;
    }

    public static EmailStepDto map(EmailStep emailStep) {
        if(emailStep == null) return null;
        return EmailStepDto.builder()
                .receiverEmail(emailStep.getReceiverEmail())
                .subject(emailStep.getSubject())
                .content(emailStep.getContent())
                .attachments(emailStep.getAttachments())
                .workflowStepId(emailStep.getWorkflowStepId())
                .id(emailStep.getId())
                .build();
    }

    public static EmailStep mapForUpdate(EmailStep emailStep, EmailStep emailStepToChange) {
        emailStep.setContent(emailStepToChange.getContent());
        emailStep.setReceiverEmail(emailStepToChange.getReceiverEmail());
        emailStep.setSubject(emailStepToChange.getSubject());
        emailStep.setAttachments(emailStepToChange.getAttachments());
        return emailStep;
    }
}
