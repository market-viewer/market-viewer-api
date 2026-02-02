package jotalac.market_viewer.market_viewer_app.service.provider;


public interface AIGenerationProvider {

    String generateText(String prompt);

    void validateApiKey(String apiKey);
}
