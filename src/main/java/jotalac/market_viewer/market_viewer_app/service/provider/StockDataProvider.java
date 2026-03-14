package jotalac.market_viewer.market_viewer_app.service.provider;

import jotalac.market_viewer.market_viewer_app.dto.api_response.stock_api.StockGraphResponse;
import jotalac.market_viewer.market_viewer_app.dto.api_response.stock_api.StockPriceResponse;
import jotalac.market_viewer.market_viewer_app.entity.screens.AssetTimeFrame;
import jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen.StockPriceData;

import java.util.List;

public interface StockDataProvider {

    StockPriceResponse fetchStockPriceData(String symbol, String apiKey);

    List<Double> fetchStockGraphData(String symbol, AssetTimeFrame assetTimeFrame, String apiKey);


    void validateApiKey(String apiKey);

    void validateAssetSymbol(String symbol, String apiKey);

    boolean acceptsTimeFrame(AssetTimeFrame assetTimeFrame);

}
