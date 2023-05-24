package fii.request.manager.service;

import fii.request.manager.domain.EmailStep;
import fii.request.manager.domain.WorkflowExecutionContext;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.hibernate.pretty.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service("EmailSenderService")
public class EmailSenderService implements StepRunnerService{

    private JavaMailSender emailSender;

    private EmailStepService emailStepService;

    @Autowired
    EmailSenderService(JavaMailSender emailSender,
                       EmailStepService emailStepService) {
        this.emailSender = emailSender;
        this.emailStepService = emailStepService;
    }

    public void sendEmail(
            String to, String subject, String text, Map<String, byte[]> attachements) {

        try {

            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("workflowmanager2023@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            attachements.forEach((fileName, fileBytes) -> {
                helperAddAttachment(helper, fileName, fileBytes);
            });

            emailSender.send(message);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void helperAddAttachment(MimeMessageHelper mimeMessageHelper, String fileName, byte[] fileBytes) {
        try {
            mimeMessageHelper.addAttachment(fileName, new ByteArrayDataSource(fileBytes, "application/octet-stream"));
        } catch(Exception e) {}
    }

    @Override
    public void runServerStep(Long workflowStepId, WorkflowExecutionContext workflowExecutionContext) {
        EmailStep emailStep = emailStepService.getByWorkflowStepId(workflowStepId);
        workflowExecutionContext.setVariable("-email-step-to", emailStep.getReceiverEmail());
        workflowExecutionContext.setVariable("-email-step-content", emailStep.getContent());
        workflowExecutionContext.setVariable("-email-step-subject", emailStep.getSubject());
        workflowExecutionContext.setVariable("-email-step-attachements", emailStep.getAttachements());

        sendEmail(workflowExecutionContext.getVariable("-email-step-to"),
                workflowExecutionContext.getVariable("-email-step-subject"),
                workflowExecutionContext.getVariable("-email-step-content"),
                new HashMap<>());
    }
}
