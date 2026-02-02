package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import jotalac.market_viewer.market_viewer_app.service.provider.AIGenerationProvider;
import org.springframework.stereotype.Component;

@Component
public class GeminiAIProvider implements AIGenerationProvider {

    @Override
    public String generateText(String prompt) {
        return "";
    }

    @Override
    public void validateApiKey(String apiKey) {

    }
}
