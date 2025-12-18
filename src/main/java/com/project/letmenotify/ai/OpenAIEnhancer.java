package com.project.letmenotify.ai;

import com.project.letmenotify.dto.OpenAIRequest;
import com.project.letmenotify.dto.OpenAIResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class OpenAIEnhancer implements AIEnhancer {

    private final WebClient webClient;

    @Value("${ai.enabled}")
    private boolean aiEnabled;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.timeout-ms}")
    private long timeoutMs;

    public OpenAIEnhancer(WebClient openAIWebClient) {
        this.webClient = openAIWebClient;
        System.out.println(">>> OpenAI Enhancer loaded");
    }

    @Override
    @RateLimiter(name = "openai", fallbackMethod = "rateLimitFallback")
    @Retry(name = "openai", fallbackMethod = "retryFallback")
    public String enhance(String baseText, String tone, String priority, String channel) {

        System.out.println(">>> AI Enhancer invoked");

        if (!aiEnabled) {
            System.out.println(">>> AI disabled");
            return baseText;
        }

        String prompt = "Rewrite this sentence using different wording:\n" + baseText;

        OpenAIRequest request = new OpenAIRequest();
        request.setModel(model);
        System.out.println(">>> Using model: " + model);
        request.setMessages(List.of(
                Map.of("role", "user", "content", prompt)
        ));

        System.out.println(">>> Calling OpenAI with prompt:\n" + prompt);

        OpenAIResponse response = webClient.post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OpenAIResponse.class)
                .timeout(Duration.ofMillis(timeoutMs))
                .block();

        System.out.println(">>> OpenAI raw response: " + response);

        return response.getChoices()
                .get(0)
                .getMessage()
                .getContent()
                .trim();
    }

    public String rateLimitFallback(String baseText, String tone, String priority, String channel, Throwable ex) {
        System.out.println("⚠️ RateLimiter fallback triggered: " + ex.getClass().getSimpleName());
        return baseText;
    }

    public String retryFallback(String baseText, String tone, String priority, String channel, Throwable ex) {
        System.out.println("⚠️ Retry fallback triggered: " + ex.getClass().getSimpleName());
        return baseText;
    }
}
