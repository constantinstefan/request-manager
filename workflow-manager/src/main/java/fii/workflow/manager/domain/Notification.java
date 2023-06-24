package fii.workflow.manager.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime time;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
