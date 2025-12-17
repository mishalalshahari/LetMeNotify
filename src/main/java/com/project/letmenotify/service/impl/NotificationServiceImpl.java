package com.project.letmenotify.service.impl;

import com.project.letmenotify.dto.NotificationRequest;
import com.project.letmenotify.dto.NotificationResponse;
import com.project.letmenotify.model.NotificationLog;
import com.project.letmenotify.repository.NotificationLogRepository;
import com.project.letmenotify.rules.NotificationRuleEngine;
import com.project.letmenotify.service.NotificationService;
import com.project.letmenotify.service.TemplateService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRuleEngine ruleEngine;
    private final TemplateService templateService;
    private final NotificationLogRepository logRepository;

    public NotificationServiceImpl(
            NotificationRuleEngine ruleEngine,
            TemplateService templateService,
            NotificationLogRepository logRepository) {

        this.ruleEngine = ruleEngine;
        this.templateService = templateService;
        this.logRepository = logRepository;
    }

    @Override
    public NotificationResponse process(NotificationRequest request) {

        // 1. Decide channel & priority
        String channel = ruleEngine.decideChannel(request.getEventType());
        String priority = ruleEngine.decidePriority(request.getEventType());

        // 2. Render base content
        String content = templateService.renderTemplate(
                request.getEventType(),
                channel,
                request.getContext()
        );

        // 3. Persist log
        NotificationLog log = new NotificationLog();
        log.setUserId(request.getUserId());
        log.setEventType(request.getEventType());
        log.setChannel(channel);
        log.setPriority(priority);
        log.setFinalContent(content);

        logRepository.save(log);

        // 4. Return response
        return new NotificationResponse(channel, priority, content);
    }
}
