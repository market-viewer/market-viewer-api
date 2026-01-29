package jotalac.market_viewer.market_viewer_app.service.provider;

import jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen.StockPriceData;

public interface StockDataProvider {

    StockPriceData fetchStockPriceData(String assetName);

    Object fetchStockGraphData();


    void validateApiKey(String apiKey);

    void validateAssetSymbol(String symbol, String apiKey);
}
