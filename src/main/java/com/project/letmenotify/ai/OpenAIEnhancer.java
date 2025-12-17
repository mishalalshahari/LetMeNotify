package com.project.letmenotify.ai;

import com.project.letmenotify.dto.OpenAIRequest;
import com.project.letmenotify.dto.OpenAIResponse;
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
    }

    @Override
    public String enhance(String baseText, String tone, String priority, String channel) {

        System.out.println(">>> AI Enhancer invoked");

        if (!aiEnabled) {
            System.out.println(">>> AI disabled");
            return baseText;
        }

        String prompt = "Rewrite this sentence using different wording:\n" + baseText;

        try {
            OpenAIRequest request = new OpenAIRequest();
            request.setModel(model);
            request.setMessages(List.of(
                    Map.of("role", "user", "content", prompt)
            ));

            OpenAIResponse response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OpenAIResponse.class)
                    .block();

            System.out.println(">>> OpenAI raw response: " + response);

            return response.getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();
        }
        catch (WebClientResponseException.TooManyRequests ex) {
                System.out.println("⚠️ OpenAI rate limit hit, falling back to base text");
                return baseText;
            }
        catch (Exception ex) {
                ex.printStackTrace();
                return baseText;
            }
    }
}
