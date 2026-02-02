package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import jotalac.market_viewer.market_viewer_app.exception.api_provider.ApiKeyNotValid;
import jotalac.market_viewer.market_viewer_app.service.provider.AIGenerationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Slf4j
@Component
public class GeminiAIProvider implements AIGenerationProvider {
    GeminiAIProvider(@Qualifier("geminiClient") RestClient restClient) {this.restClient = restClient;}

    private final RestClient restClient;

    @Override
    public String generateText(String prompt) {
        return "";
    }

    @Override
    public void validateApiKey(String apiKey) {
        restClient.get()
                .uri("/v1/models")
                .header("x-goog-api-key", apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new ApiKeyNotValid("Gemini api key not valid");
                })
                .toBodilessEntity(); // validates status
    }
}
