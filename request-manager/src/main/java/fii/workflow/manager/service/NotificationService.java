package fii.workflow.manager.service;

import fii.workflow.manager.domain.Notification;
import fii.workflow.manager.dto.NotificationDto;
import fii.workflow.manager.mapper.NotificationMapper;
import fii.workflow.manager.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;
    private CustomerService customerService;

    private WebsocketNotificationService websocketNotificationService;

    @Autowired
    NotificationService(NotificationRepository notificationRepository,
                        CustomerService customerService,
                        WebsocketNotificationService websocketNotificationService) {
        this.notificationRepository = notificationRepository;
        this.customerService = customerService;
        this.websocketNotificationService = websocketNotificationService;
    }

    public List<NotificationDto> getNotifications() {
        String principalEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return notificationRepository.findByCustomer_EmailOrderByTimeDesc(principalEmail).stream()
                .map(NotificationMapper::map).collect(Collectors.toList());
    }

    public NotificationDto addNotification(Long customerId, NotificationDto notificationDto) {
        Notification notification = NotificationMapper.map(notificationDto);
        notification.setCustomer(customerService.getById(customerId));
        notificationDto = NotificationMapper.map(notificationRepository.save(notification));
        websocketNotificationService.sendNotification(notificationDto);
        return notificationDto;
    }

    public NotificationDto sendNotification(Long customerId, String message) {
        NotificationDto notificationDto = NotificationDto.builder()
                .message(message)
                .build();

        return addNotification(customerId, notificationDto);
    }
}
