package jotalac.market_viewer.market_viewer_api.service.provider.impl;

import jotalac.market_viewer.market_viewer_api.exception.api_provider.ApiKeyNotValid;
import jotalac.market_viewer.market_viewer_api.exception.api_provider.ApiResponseError;
import jotalac.market_viewer.market_viewer_api.service.provider.AIGenerationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

@Slf4j
@Component
public class GeminiAIProvider implements AIGenerationProvider {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${app.ai.gemini.model}")
    private String geminiModel;

    GeminiAIProvider(@Qualifier("geminiClient") RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }


    @Override
    public String generateText(String prompt, String apiKey) {
        String requestBody = buildJsonRequest(prompt);

        JsonNode response = restClient.post()
                .uri("/v1beta/models/" + geminiModel + ":generateContent")
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    log.warn(res.getStatusCode().toString());
                    log.warn(res.getBody().toString());
                    throw new ApiResponseError("Error calling gemini api:" + res.getStatusCode().toString());
                })
                .body(JsonNode.class);

        return extractTextFromResponse(response);
    }

    @Override
    public void validateApiKey(String apiKey) {
        restClient.get()
                .uri("/v1/models")
                .header("x-goog-api-key", apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    log.info(res.getStatusCode().toString());
                    throw new ApiKeyNotValid("Gemini api key not valid");
                })
                .toBodilessEntity(); // validates status
    }

    //create request body with all parts
    private String buildJsonRequest(String prompt) {
        ObjectNode root = objectMapper.createObjectNode();


        String systemPrompt = """
            You are creating short message that will be displayed on 400x400px IoT display.
            RULES:
            0. when asked about finances dont output just data, more about sentiment and what is currently happening and WHY is it happening
            1. Search for REAL-TIME data when asked about finances.
            2. Output should be plain text (NO markdown, no symbols, no emoji).
            3. MAX 50 words total.
            4. Format and serparate the text in readable form for small display
            """;

        //add system prompt
        ObjectNode systemInstructions = root.putObject("system_instruction");
        systemInstructions.putArray("parts").addObject().put("text", systemPrompt);

        //enable google search
        ArrayNode tools = root.putArray("tools");
        tools.addObject().putObject("google_search");

        //add user prompt
        ArrayNode contents = root.putArray("contents");
        ObjectNode contentPart = contents.addObject();
        contentPart.putArray("parts").addObject().put("text", prompt);

        return root.toString();
    }

    private String extractTextFromResponse(JsonNode json) {
        if (json == null || !json.has("candidates") || json.path("candidates").isEmpty()) {
            return "No response available";
        }

        JsonNode firstCandidate = json.path("candidates").get(0);

        return firstCandidate.path("content")
                .path("parts").get(0)
                .path("text").asString()
                .trim();
    }
}
