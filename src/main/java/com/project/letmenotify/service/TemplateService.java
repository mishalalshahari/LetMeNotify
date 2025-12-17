package com.project.letmenotify.service;

import java.util.Map;

public interface TemplateService {

    private String renderTemplate(String eventType, String channel, Map<String, Object> context);

    private String replacePlaceholders(String templateText, Map<String, Object> context);
}
