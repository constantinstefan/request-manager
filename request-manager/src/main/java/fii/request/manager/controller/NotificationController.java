package fii.request.manager.controller;

import fii.request.manager.dto.NotificationDto;
import fii.request.manager.dto.WorkflowStepDto;
import fii.request.manager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private NotificationService notificationService;

    @Autowired
    NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    List<NotificationDto> getNotifications() {
        return notificationService.getNotifications();
    }
}
