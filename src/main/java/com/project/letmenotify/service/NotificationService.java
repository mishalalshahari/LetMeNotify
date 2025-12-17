package com.project.letmenotify.service;

import com.project.letmenotify.dto.NotificationRequest;
import com.project.letmenotify.dto.NotificationResponse;

public interface NotificationService {

    NotificationResponse process(NotificationRequest request);
}
