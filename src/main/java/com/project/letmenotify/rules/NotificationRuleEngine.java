package com.project.letmenotify.rules;

import org.springframework.stereotype.Component;

@Component
public class NotificationRuleEngine {

    public String decideChannel(String eventType) {

        if (eventType == null) {
            return "PUSH";
        }

        if (eventType.contains("PAYMENT")) {
            return "EMAIL";
        }

        if (eventType.contains("SECURITY")) {
            return "EMAIL";
        }

        if (eventType.contains("LOGIN")) {
            return "PUSH";
        }

        return "PUSH";
    }

    public String decidePriority(String eventType) {

        if (eventType == null) {
            return "LOW";
        }

        if (eventType.contains("FAILED")) {
            return "HIGH";
        }

        if (eventType.contains("EXPIRED")) {
            return "HIGH";
        }

        if (eventType.contains("REMINDER")) {
            return "NORMAL";
        }

        return "LOW";
    }
}
