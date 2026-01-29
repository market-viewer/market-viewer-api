package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen.StockPriceData;
import jotalac.market_viewer.market_viewer_app.exception.api_provider.ApiKeyNotValid;
import jotalac.market_viewer.market_viewer_app.exception.api_provider.ApiRateLimitExceededException;
import jotalac.market_viewer.market_viewer_app.exception.api_provider.AssetNameNotValid;
import jotalac.market_viewer.market_viewer_app.service.provider.StockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.time.Duration;
import java.util.Objects;

@Service
@Slf4j
public class TwelveDataStockProvider implements StockDataProvider {
    TwelveDataStockProvider(@Qualifier("twelveDataClient") RestClient restClient) {this.restClient = restClient;}

    private final RestClient restClient;

    //caching valid coin names
    private final Cache<String, Boolean> validSymbols = Caffeine.newBuilder()
            .maximumSize(100_000)
            .expireAfterWrite(Duration.ofDays(100))
            .build();


    @Override
    public StockPriceData fetchStockPriceData(String assetName) {
        return null;
    }

    @Override
    public Object fetchStockGraphData() {
        return null;
    }

    @Override
    public void validateApiKey(String apiKey) {
        JsonNode response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api_usage")
                        .queryParam("apikey", apiKey)
                        .build()
                )
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new ApiKeyNotValid("TwelveData api key is not valid");
                })
                .body(JsonNode.class);

        if (!isResponseValid(response)) {
            throw new ApiKeyNotValid("TwelveData api key is not valid");
        }
    }

    @Override
    public void validateAssetSymbol(String symbol, String apiKey) {
        validSymbols.get(symbol, name -> {
            performSymbolValidation(symbol, apiKey);
            return true;
        });
    }

    private void performSymbolValidation(String symbol, String apiKey) {
        JsonNode response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("price")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                //handle any error
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new IllegalStateException("CoinGecko API Error [" + res.getStatusCode() + "]: " + res.getBody());
                })
                .body(JsonNode.class);

        if (!isResponseValid(response)) {
            throw new AssetNameNotValid("Symbol '" + symbol + "' not found");
        }
    }

    // for some reason the twelve data api doesnt return the actual code in the response code but in the body :(
    private boolean isResponseValid(JsonNode response) {
        if (response == null) {
            return false;
        }
        // if error status is present in the response body
        JsonNode bodyResponseStatus = response.get("code");
        if (bodyResponseStatus != null) {
            if (bodyResponseStatus.asInt() == 429) {
                throw new ApiRateLimitExceededException("TwelveData api rate limit exceeded");
            }
            return false;
        }
        return true;
    }


}
