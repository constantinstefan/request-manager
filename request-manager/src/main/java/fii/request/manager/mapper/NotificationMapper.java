package fii.request.manager.mapper;

import fii.request.manager.domain.Notification;
import fii.request.manager.dto.NotificationDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class NotificationMapper {
    public static NotificationDto map(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .isOpen(notification.getIsOpen())
                .message(notification.getMessage())
                .time(notification.getTime().toEpochSecond(ZoneOffset.UTC))
                .build();
    }

    public static Notification map(NotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setId(notificationDto.getId());
        notification.setTime(LocalDateTime.now());
        notification.setMessage(notificationDto.getMessage());
        notification.setIsOpen(false);
        return notification;
    }
}
