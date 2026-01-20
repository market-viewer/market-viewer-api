package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import jotalac.market_viewer.market_viewer_app.entity.screens.StockPriceData;
import jotalac.market_viewer.market_viewer_app.service.provider.StockDataProvider;
import org.springframework.stereotype.Service;

@Service
public class FinHubStockProvider implements StockDataProvider {
    @Override
    public StockPriceData fetchStockPriceData(String assetName) {
        return null;
    }
}
