package com.project.letmenotify.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class NotificationRequest {

    @NotBlank
    private String eventType;

    private Long userId;

    private Map<String, Object> context;
}
