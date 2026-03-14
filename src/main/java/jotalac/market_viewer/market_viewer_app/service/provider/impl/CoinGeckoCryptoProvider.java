package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jotalac.market_viewer.market_viewer_app.dto.api_response.crypto_api.CryptoGraphResponse;
import jotalac.market_viewer.market_viewer_app.dto.api_response.crypto_api.CryptoPriceResponse;
import jotalac.market_viewer.market_viewer_app.entity.screens.AssetTimeFrame;
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
    public CryptoPriceResponse fetchCryptoPriceData(String currency, String assetName, AssetTimeFrame assetTimeFrame, String apiKey) {
        String timeFrameValue = getPriceChangePercentage(assetTimeFrame);

        List<CryptoPriceResponse> responseList = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("coins/markets")
                        .queryParam("vs_currency", currency)
                        .queryParam("ids", assetName)
                        .queryParam("price_change_percentage", getPriceChangePercentage(assetTimeFrame))
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
    public List<Double> fetchCryptoGraphData(String currency, String assetName, AssetTimeFrame assetTimeFrame, String apiKey) {
        CryptoGraphResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/coins/{id}/market_chart")
                        .queryParam("vs_currency", currency)
                        .queryParam("days", getDays(assetTimeFrame))
                        .build(assetName.toLowerCase()))
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
        log.warn(req.getURI().toString());
        throw new ApiResponseError("CoinGecko API Error [" + res.getStatusCode() + "]: " + res.getBody());
    }

    // helper methods to handle time frames for crypto
    @Override
    public boolean acceptsTimeFrame(AssetTimeFrame assetTimeFrame) {
        return switch (assetTimeFrame) {
            case HOUR, DAY, WEEK, TWO_WEEKS, MONTH, TWO_HUNDRED_DAYS, YEAR -> true;
            default -> false;
        };
    }


    private String getPriceChangePercentage(AssetTimeFrame timeFrame) {
        return switch (timeFrame) {
            case HOUR -> "1h";
            case DAY -> "24h";
            case WEEK -> "7d";
            case TWO_WEEKS -> "14d";
            case MONTH -> "30d";
            case TWO_HUNDRED_DAYS -> "200d";
            case YEAR -> "1y";
            default -> {
                log.warn("Time frame {} is not supported by CoinGecko (Price Change), defaulting to 24h", timeFrame);
                yield "24h";
            }
        };

    }

    private String getDays(AssetTimeFrame timeFrame) {
        return switch (timeFrame) {
            case HOUR, DAY -> "1";
            case WEEK -> "7";
            case TWO_WEEKS -> "14";
            case MONTH -> "30";
            case TWO_HUNDRED_DAYS -> "200";
            case YEAR -> "365";
            default -> {
                log.warn("Time frame {} is not supported by CoinGecko (Graph), defaulting to 1 day", timeFrame);
                yield "1";
            }
        };
    }
}
