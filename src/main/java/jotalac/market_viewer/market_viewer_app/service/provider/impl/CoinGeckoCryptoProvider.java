package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jotalac.market_viewer.market_viewer_app.dto.api_response.crypto_api.CryptoGraphResponse;
import jotalac.market_viewer.market_viewer_app.dto.api_response.crypto_api.CryptoPriceResponse;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoTimeFrame;
import jotalac.market_viewer.market_viewer_app.exception.api_provider.ApiKeyNotValid;
import jotalac.market_viewer.market_viewer_app.exception.api_provider.ApiRateLimitExceededException;
import jotalac.market_viewer.market_viewer_app.exception.api_provider.ApiResponseError;
import jotalac.market_viewer.market_viewer_app.exception.api_provider.AssetNameNotValid;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class CoinGeckoCryptoProvider implements CryptoDataProvider {
    CoinGeckoCryptoProvider(@Qualifier("coingeckoClient") RestClient restClient) {this.restClient = restClient;}

    private final RestClient restClient;

    //caching valid coin names
    private final Cache<String, Boolean> validCoinNames = Caffeine.newBuilder()
            .maximumSize(20_000)
            .expireAfterWrite(Duration.ofDays(30))
            .build();

    @Override
    public CryptoPriceResponse fetchCryptoPriceData(String currency, String assetName, CryptoTimeFrame timeFrame, String apiKey) {
        List<CryptoPriceResponse> responseList = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("coins/markets")
                        .queryParam("vs_currency", currency)
                        .queryParam("ids", assetName)
                        .queryParam("price_change_percentage", timeFrame.getValue())
                        .build())
                .header("x-cg-demo-api-key", apiKey)
                .retrieve()
                //handle any error
                .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                .body(new ParameterizedTypeReference<List<CryptoPriceResponse>>() {});

        log.info("Fetching price data for: {}", assetName);


        if (responseList == null || responseList.isEmpty()) {
            throw new IllegalStateException("No data found for " + assetName);
        }

        return responseList.getFirst();
    }

    @Override
    public List<Double> fetchCryptoGraphData(String currency, String assetName, CryptoTimeFrame timeFrame, String apiKey) {
        CryptoGraphResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/coins/{id}/market_chart")
                        .queryParam("vs_currency", currency)
                        .queryParam("days", timeFrame.convertToDays())
                        .build(assetName))
                .header("x-cg-demo-api-key", apiKey)
                .retrieve()
                //handle any error
                .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                .body(CryptoGraphResponse.class);

        log.info("Fetching graph data for: {}", assetName);


        if (response == null) {
            throw new IllegalStateException("No graph data found for " + assetName);
        }

        return response.getReducedPrices();
    }

    @Override
    public void validateApiKey(String apiKey) {
        restClient.get()
                .uri("ping")
                .header("x-cg-demo-api-key", apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                .toBodilessEntity();
    }

    @Override
    public void validateCoinName(String assetName, String apiKey) {
        // check coin name from cache
        validCoinNames.get(assetName, name -> {
            performCoinValidation(name, apiKey);
            return true;
        });
    }

    private void performCoinValidation(String assetName, String apiKey) {
        List<CryptoPriceResponse> responseList = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("coins/markets")
                        .queryParam("vs_currency", "usd")
                        .queryParam("ids", assetName)
                        .build())
                .header("x-cg-demo-api-key", apiKey)
                .retrieve()
                //handle any error
                .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                .body(new ParameterizedTypeReference<>() {
                });

        log.info("Validating coin name: {}", assetName);

        if (responseList == null || responseList.isEmpty()) {
            throw new AssetNameNotValid("Coin name '" + assetName + "' not found");
        }
    }

    @Override
    public void handleErrorResponse(HttpRequest req, ClientHttpResponse res) throws IOException {
        if (res.getStatusCode().value() == 429) {
            throw new ApiRateLimitExceededException("CoinGecko rate limit exceeded");
        }
        throw new ApiResponseError("CoinGecko API Error [" + res.getStatusCode() + "]: " + res.getBody());
    }
}
