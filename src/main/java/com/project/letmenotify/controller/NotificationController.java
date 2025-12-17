package com.project.letmenotify.controller;

import com.project.letmenotify.dto.NotificationRequest;
import com.project.letmenotify.dto.NotificationResponse;
import com.project.letmenotify.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public NotificationResponse createNotification(
            @Valid @RequestBody NotificationRequest request) {

        return service.process(request);
    }
}
