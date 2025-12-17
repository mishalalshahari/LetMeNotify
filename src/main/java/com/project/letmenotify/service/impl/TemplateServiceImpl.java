package com.project.letmenotify.service.impl;

import com.project.letmenotify.model.NotificationTemplate;
import com.project.letmenotify.repository.NotificationTemplateRepository;
import com.project.letmenotify.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final NotificationTemplateRepository repository;

    public String renderTemplate(String eventType, String channel, Map<String, Object> context) {

        NotificationTemplate template = repository
                .findByEventTypeAndChannel(eventType, channel)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No template found for event=" + eventType + ", channel=" + channel));

        return replacePlaceholders(template.getTemplateText(), context);
    }

    public String replacePlaceholders(String templateText, Map<String, Object> context) {

        if (context == null || context.isEmpty()) {
            return templateText;
        }

        String rendered = templateText;

        for (Map.Entry<String, Object> entry : context.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}";
            String value = entry.getValue() == null ? "" : entry.getValue().toString();
            rendered = rendered.replace(placeholder, value);
        }

        return rendered;
    }
}
