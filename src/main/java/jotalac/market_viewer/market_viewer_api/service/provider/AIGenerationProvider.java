package jotalac.market_viewer.market_viewer_api.service.provider;


public interface AIGenerationProvider {

    String generateText(String prompt, String apiKey);

    void validateApiKey(String apiKey);
}
