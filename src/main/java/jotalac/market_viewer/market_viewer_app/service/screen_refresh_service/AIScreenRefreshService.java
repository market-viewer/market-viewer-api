package jotalac.market_viewer.market_viewer_app.service.screen_refresh_service;

import jotalac.market_viewer.market_viewer_app.entity.ApiKey;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyProvider;
import jotalac.market_viewer.market_viewer_app.entity.screens.AITextScreen;
import jotalac.market_viewer.market_viewer_app.exception.user.MissingApiKey;
import jotalac.market_viewer.market_viewer_app.repository.ApiKeyRepository;
import jotalac.market_viewer.market_viewer_app.service.provider.AIGenerationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AIScreenRefreshService {

   private final AIGenerationProvider aiGenerationProvider;
   private final ApiKeyRepository apiKeyRepository;

   public void refreshAiScreen(AITextScreen aiTextScreen) {
      ApiKey userApiKey = apiKeyRepository.findByEndpointAndUser(ApiKeyProvider.GEMINI, aiTextScreen.getDevice().getUser())
              .orElseThrow(() -> new MissingApiKey("Missing api key"));

      String newText = aiGenerationProvider.generateText(aiTextScreen.getPrompt(), userApiKey.getValue());

      aiTextScreen.setLastFetchTime(LocalDateTime.now());
      aiTextScreen.setDisplayText(newText);
   }
}
