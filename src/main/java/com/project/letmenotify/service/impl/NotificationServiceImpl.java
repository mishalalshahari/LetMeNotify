package com.project.letmenotify.service.impl;

import com.project.letmenotify.ai.AIEnhancer;
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
    private final AIEnhancer aiEnhancer;

    public NotificationServiceImpl(
            NotificationRuleEngine ruleEngine,
            TemplateService templateService,
            NotificationLogRepository logRepository,
            AIEnhancer aiEnhancer) {

        this.ruleEngine = ruleEngine;
        this.templateService = templateService;
        this.logRepository = logRepository;
        this.aiEnhancer = aiEnhancer;
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

        // 3. Render enhanced content
        String enhancedContent = aiEnhancer.enhance(
                content,
                "FRIENDLY",
                priority,
                channel
        );

//        if ("HIGH".equals(priority)) {
//            enhancedContent = aiEnhancer.enhance(
//                    content,
//                    "FRIENDLY",
//                    priority,
//                    channel
//            );
//        }

        // 4. Persist log
        NotificationLog log = new NotificationLog();
        log.setUserId(request.getUserId());
        log.setEventType(request.getEventType());
        log.setChannel(channel);
        log.setPriority(priority);
        log.setFinalContent(enhancedContent);

        logRepository.save(log);

        // 5. Return response
        return new NotificationResponse(channel, priority, enhancedContent);
    }
}
