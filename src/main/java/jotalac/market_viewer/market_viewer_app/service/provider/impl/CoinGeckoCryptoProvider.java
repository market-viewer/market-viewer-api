package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import jotalac.market_viewer.market_viewer_app.dto.api_response.coingecko.CoinGeckoPriceResponse;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoPriceData;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoTimeFrame;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CoinGeckoCryptoProvider implements CryptoDataProvider {
    CoinGeckoCryptoProvider(@Qualifier("coingeckoClient") RestClient restClient) {this.restClient = restClient;}

    private final RestClient restClient;

    @Override
    public CoinGeckoPriceResponse fetchCryptoPriceData(String currency, String assetName, CryptoTimeFrame timeFrame, String apiKey) {
        List<CoinGeckoPriceResponse> responseList = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("coins/markets")
                        .queryParam("vs_currency", currency)
                        .queryParam("ids", assetName)
//                        .queryParam("price_change_percentage", timeFrame.getValue())
                        .queryParam("price_change_percentage", "24h")
                        .build())
                .header("x-cg-demo-api-key", apiKey)
                .retrieve()
                //handle any error
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    // Start: Better error handling
                    String responseBody = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
                    throw new IllegalStateException("CoinGecko API Error [" + res.getStatusCode() + "]: " + responseBody);
                    // End: Better error handling
                })
                .body(new ParameterizedTypeReference<List<CoinGeckoPriceResponse>>() {});

        if (responseList == null || responseList.isEmpty()) {
            throw new IllegalStateException("No data found for " + assetName);
        }

        return responseList.getFirst();
    }

    @Override
    public List<Double> fetchCryptoGraphData(String something) {
        return List.of();
    }

    @Override
    public Boolean validateApiKey(String apiKey) {
        return null;
    }
}
