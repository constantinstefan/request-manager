package fii.request.manager.service;

import fii.request.manager.domain.Execution;
import fii.request.manager.domain.Notification;
import fii.request.manager.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketNotificationService {

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    WebsocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendExecutionNotification(Execution message) {
        String topic = "/execution/" + message.getId();
        messagingTemplate.convertAndSend(topic, message);
    }

    public void sendNotification(NotificationDto notification) {
        String topic = "/notifications";
        messagingTemplate.convertAndSend(topic, notification);
    }
}
