package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import jotalac.market_viewer.market_viewer_app.exception.api_provider.ApiKeyNotValid;
import jotalac.market_viewer.market_viewer_app.service.provider.AIGenerationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class GeminiAIProvider implements AIGenerationProvider {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    GeminiAIProvider(@Qualifier("geminiClient") RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }


    @Override
    public String generateText(String prompt, String apiKey) {
        return "";
    }

    @Override
    public void validateApiKey(String apiKey) {
        try {
            restClient.get()
                    .uri("/v1/models")
                    .header("x-goog-api-key", apiKey)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) -> {
                        throw new ApiKeyNotValid("Gemini api key not valid");
                    })
                    .toBodilessEntity(); // validates status
        } catch (Exception e) {
            throw new ApiKeyNotValid("Unexpected error occurred during Gemini api key validation");
        }
    }
}
