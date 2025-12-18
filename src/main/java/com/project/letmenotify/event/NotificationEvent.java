package com.project.letmenotify.event;

import com.project.letmenotify.dto.NotificationRequest;

public record NotificationEvent(NotificationRequest request) {
}
