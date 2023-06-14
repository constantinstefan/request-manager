package fii.request.manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fii.request.manager.domain.Customer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NotificationDto {
    private Long id;

    private String message;

    private Long time;

    private Boolean isOpen;
}
