package fii.request.manager.service;

import fii.request.manager.domain.EmailStep;
import fii.request.manager.domain.WorkflowExecutionContext;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("EmailSenderService")
public class EmailSenderService implements StepRunnerService{

    private JavaMailSender emailSender;

    private EmailStepService emailStepService;

    private static boolean shouldSendEmail = true;

    @Autowired
    EmailSenderService(JavaMailSender emailSender,
                       EmailStepService emailStepService) {
        this.emailSender = emailSender;
        this.emailStepService = emailStepService;
    }

    public void sendEmail(String to, String subject, String text, Map<String, byte[]> attachements) {

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

            if(shouldSendEmail) {
                emailSender.send(message);
            }
            System.out.println("email " + to + subject + text + attachements);
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

        sendEmail(workflowExecutionContext.resolveVariable(emailStep.getReceiverEmail()),
                workflowExecutionContext.resolveVariable(emailStep.getSubject()),
                workflowExecutionContext.resolveVariable(emailStep.getContent()),
                workflowExecutionContext.getFiles(emailStep.getAttachments()));
    }
}
