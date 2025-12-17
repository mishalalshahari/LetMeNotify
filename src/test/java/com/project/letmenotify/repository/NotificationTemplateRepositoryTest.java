package com.project.letmenotify.repository;

import com.project.letmenotify.model.NotificationTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class NotificationTemplateRepositoryTest {

    @Autowired
    private NotificationTemplateRepository repository;

    @Test
    void shouldFindTemplateByEventAndChannel() {
        NotificationTemplate template = new NotificationTemplate();
        template.setEventType("PAYMENT_FAILED");
        template.setChannel("EMAIL");
        template.setTone("FRIENDLY");
        template.setTemplateText("Test");

        repository.save(template);

        Optional<NotificationTemplate> result =
                repository.findByEventTypeAndChannel("PAYMENT_FAILED", "EMAIL");

        assertTrue(result.isPresent());
    }
}
