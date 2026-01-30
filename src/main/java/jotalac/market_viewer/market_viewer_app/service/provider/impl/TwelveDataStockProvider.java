package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jotalac.market_viewer.market_viewer_app.dto.api_response.stock_api.StockGraphResponse;
import jotalac.market_viewer.market_viewer_app.dto.api_response.stock_api.StockPriceResponse;
import jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen.StockPriceData;
import jotalac.market_viewer.market_viewer_app.exception.api_provider.*;
import jotalac.market_viewer.market_viewer_app.service.provider.StockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class TwelveDataStockProvider implements StockDataProvider {
    TwelveDataStockProvider(@Qualifier("twelveDataClient") RestClient restClient) {this.restClient = restClient;}

    private final RestClient restClient;

    //caching valid coin names
    private final Cache<String, Boolean> validSymbols = Caffeine.newBuilder()
            .maximumSize(100_000)
            .expireAfterWrite(Duration.ofDays(100))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public StockPriceResponse fetchStockPriceData(String symbol, String apiKey) {
        JsonNode response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/quote")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", apiKey)
                        .build()
                )
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new ApiResponseError("TwelveData price - response error");
                })
                .body(JsonNode.class);

        if (!isResponseValid(response)) {
            log.warn(String.valueOf(response));
            throw new ApiResponseError("TwelveData price fetch error:" + response.get("message").asString());
        }

        return objectMapper.convertValue(response, StockPriceResponse.class);
    }

    @Override
    public List<Double> fetchStockGraphData(String symbol, String interval, Integer outputSize, String apiKey) {
        JsonNode response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/time_series")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("outputsize", outputSize)
                        .queryParam("apikey", apiKey)
                        .build()
                )
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new ApiResponseError("TwelveData graph fetch error");
                })
                .body(JsonNode.class);

        if (!isResponseValid(response)) {
            throw new ApiKeyNotValid("TwelveData api error: " + response.get("message").asString() );
        }
        StockGraphResponse graphData = objectMapper.convertValue(response, StockGraphResponse.class);

        return graphData.getReducedPrices();
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
                    throw new IllegalStateException("Twelve data API Error [" + res.getStatusCode() + "]: " + res.getBody());
                })
                .body(JsonNode.class);

        if (!isResponseValid(response)) {
            throw new AssetNameNotValid("Symbol '" + symbol + "' not availible");
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
