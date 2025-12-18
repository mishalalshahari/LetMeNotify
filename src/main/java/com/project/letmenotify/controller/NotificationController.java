package com.project.letmenotify.controller;

import com.project.letmenotify.dto.NotificationRequest;
import com.project.letmenotify.dto.NotificationResponse;
import com.project.letmenotify.event.NotificationEvent;
import com.project.letmenotify.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final ApplicationEventPublisher eventPublisher;

    public NotificationController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<String> createNotification(
            @Valid @RequestBody NotificationRequest request
    ) {
        // Publish async event
        eventPublisher.publishEvent(new NotificationEvent(request));

        // Return immediately (non-blocking)
        return ResponseEntity
                .accepted()
                .body("Notification accepted for async processing");
    }
}
