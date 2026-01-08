package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import jotalac.market_viewer.market_viewer_app.service.provider.AiGenerationProvider;
import org.springframework.stereotype.Service;

@Service
public class GeminiAiProvider implements AiGenerationProvider {

    @Override
    public String generateText(String prompt) {
        return "";
    }
}
