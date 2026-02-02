package jotalac.market_viewer.market_viewer_app.service.screen_refresh_service;

import jotalac.market_viewer.market_viewer_app.entity.screens.AITextScreen;
import jotalac.market_viewer.market_viewer_app.repository.ApiKeyRepository;
import jotalac.market_viewer.market_viewer_app.service.provider.AIGenerationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIScreenRefreshService {

   private final AIGenerationProvider aiGenerationProvider;
   private final ApiKeyRepository apiKeyRepository;

   public void refreshAiScreen(AITextScreen aiTextScreen) {
       //refresh screen
   }
}
