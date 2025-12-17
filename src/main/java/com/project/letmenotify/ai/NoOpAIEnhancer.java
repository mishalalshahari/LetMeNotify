package com.project.letmenotify.ai;

import org.springframework.stereotype.Component;

@Component
public class NoOpAIEnhancer implements AIEnhancer {

    @Override
    public String enhance(String baseText, String tone, String priority, String channel) {
        return baseText;
    }
}
