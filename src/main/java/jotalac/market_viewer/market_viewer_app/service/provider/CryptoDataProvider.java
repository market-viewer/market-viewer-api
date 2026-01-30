package jotalac.market_viewer.market_viewer_app.service.provider;

import jotalac.market_viewer.market_viewer_app.dto.api_response.crypto_api.CryptoPriceResponse;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoTimeFrame;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.HttpRequest;

import java.io.IOException;
import java.util.List;

public interface CryptoDataProvider {

    CryptoPriceResponse fetchCryptoPriceData(String currency, String assetName, CryptoTimeFrame timeFrame, String apiKey);

    List<Double> fetchCryptoGraphData(String currency, String assetName, CryptoTimeFrame timeFrame, String apiKey);

    void validateApiKey(String apiKey);

    void validateCoinName(String assetName, String apiKey);

    void handleErrorResponse(HttpRequest req, ClientHttpResponse res) throws IOException;

}
