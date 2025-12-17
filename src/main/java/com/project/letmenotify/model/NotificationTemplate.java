package com.project.letmenotify.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "notification_template",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"event_type", "channel"})
        })
@Data
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type", nullable = false)
    private String eventType;      // PAYMENT_FAILED

    @Column(nullable = false)
    private String channel;        // EMAIL, SMS, PUSH

    @Column(nullable = false)
    private String tone;           // FRIENDLY, FORMAL

    @Lob
    @Column(name = "template_text", nullable = false)
    private String templateText;
}
