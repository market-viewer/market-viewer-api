package jotalac.market_viewer.market_viewer_app.service.screen_refresh_service;

import jotalac.market_viewer.market_viewer_app.dto.api_response.stock_api.StockPriceResponse;
import jotalac.market_viewer.market_viewer_app.entity.ApiKey;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyProvider;
import jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen.StockScreen;
import jotalac.market_viewer.market_viewer_app.exception.user.MissingApiKey;
import jotalac.market_viewer.market_viewer_app.repository.ApiKeyRepository;
import jotalac.market_viewer.market_viewer_app.service.provider.StockDataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class StockScreenRefreshService {

    private final StockDataProvider stockDataProvider;
    private final ApiKeyRepository apiKeyRepository;


    public void refreshStockScreen(StockScreen stockScreen) {
        ApiKey userApiKey = apiKeyRepository.findByEndpointAndUser(ApiKeyProvider.TWELVE_DATA, stockScreen.getDevice().getUser())
                .orElseThrow(() -> new MissingApiKey("Missing api key"));

        updatePrice(stockScreen, userApiKey.getValue());
        updateGraph(stockScreen, userApiKey.getValue());
    }

    private void updatePrice(StockScreen stockScreen, String apiKey) {
        StockPriceResponse newPriceData = stockDataProvider.fetchStockPriceData(
                stockScreen.getSymbol(), apiKey
        );

        var stockScreenPriceData = stockScreen.getPriceData();
        stockScreenPriceData.setLastFetchTime(LocalDateTime.now());
        stockScreenPriceData.setName(newPriceData.name());
        stockScreenPriceData.setCurrency(newPriceData.currency());
        stockScreenPriceData.setPrice(newPriceData.price());
        stockScreenPriceData.setIsMarketOpen(newPriceData.isMarketOpen());
    }

    private void updateGraph(StockScreen stockScreen, String apiKey) {
        var stockPriceData = stockScreen.getPriceData();

        List<Double> newGraphData = stockDataProvider.fetchStockGraphData(
                stockScreen.getSymbol(), stockScreen.getTimeFrame(), apiKey
        );

        // calculate price change from the current price and last price
        Double priceChange = ((stockPriceData.getPrice() - newGraphData.getFirst()) / newGraphData.getFirst()) * 100;

        stockPriceData.setLastFetchTime(LocalDateTime.now());
        stockPriceData.setPriceChange(priceChange);

        //check if we need the graph data
        stockPriceData.setGraphData(stockScreen.getDisplayGraph() ? newGraphData : Collections.emptyList());
    }
}
