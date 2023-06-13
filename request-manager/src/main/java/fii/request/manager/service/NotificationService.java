package fii.request.manager.service;

import fii.request.manager.domain.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendExecutionNotification(Execution message) {
        String topic = "/execution/" + message.getId();
        messagingTemplate.convertAndSend(topic, message);
    }
}
