
package fii.workflow.manager.service;

import fii.workflow.manager.dto.ExecutionDto;
import fii.workflow.manager.dto.NotificationDto;
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
        sendExecutionNotifactionOnTopic("/executions/" + workflowId, message);
        sendExecutionNotifactionOnTopic("/executions/" + workflowId + "/" + message.getId(), message);
    }

    public void sendNotification(NotificationDto notification) {
        String topic = "/notifications";
        System.out.println("sending message to " + topic);
        messagingTemplate.convertAndSend(topic, notification);
    }

    private void sendExecutionNotifactionOnTopic(String topic, ExecutionDto message) {
        System.out.println("sending message to " + topic);
        messagingTemplate.convertAndSend(topic, message);
    }
}
