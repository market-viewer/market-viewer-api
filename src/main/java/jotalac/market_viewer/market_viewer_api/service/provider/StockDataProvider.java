package jotalac.market_viewer.market_viewer_api.service.provider;

import jotalac.market_viewer.market_viewer_api.dto.api_response.stock_api.StockPriceResponse;
import jotalac.market_viewer.market_viewer_api.entity.screens.AssetTimeFrame;

import java.util.List;

public interface StockDataProvider {

    StockPriceResponse fetchStockPriceData(String symbol, String apiKey);

    List<Double> fetchStockGraphData(String symbol, AssetTimeFrame assetTimeFrame, String apiKey);


    void validateApiKey(String apiKey);

    void validateAssetSymbol(String symbol, String apiKey);

    boolean acceptsTimeFrame(AssetTimeFrame assetTimeFrame);

}
