package jotalac.market_viewer.market_viewer_app.service.provider;

import jotalac.market_viewer.market_viewer_app.entity.screens.StockPriceData;

public interface StockDataProvider {

    StockPriceData fetchStockPriceData(String assetName);
}
