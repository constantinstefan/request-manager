package fii.request.manager.service;

import fii.request.manager.domain.Execution;
import fii.request.manager.domain.Notification;
import fii.request.manager.dto.ExecutionDto;
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

    public void sendExecutionNotification(Long workflowId, ExecutionDto message) {
        String topic = "/executions";
        System.out.println("sending message to " + topic);
        messagingTemplate.convertAndSend(topic, message);
    }

    public void sendNotification(NotificationDto notification) {
        String topic = "/notifications";
        System.out.println("sending message to " + topic);
        messagingTemplate.convertAndSend(topic, notification);
    }
}
