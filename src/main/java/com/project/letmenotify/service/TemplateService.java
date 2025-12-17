package com.project.letmenotify.service;

import java.util.Map;

public interface TemplateService {

    public String renderTemplate(String eventType, String channel, Map<String, Object> context);

    public String replacePlaceholders(String templateText, Map<String, Object> context);
}
