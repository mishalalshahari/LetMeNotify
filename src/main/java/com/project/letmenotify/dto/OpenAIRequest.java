package com.project.letmenotify.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OpenAIRequest {

    private String model;

    // REQUIRED for /chat/completions
    private List<Map<String, String>> messages;
}
