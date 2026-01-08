package jotalac.market_viewer.market_viewer_app.service;

import jotalac.market_viewer.market_viewer_app.service.provider.AiGenerationProvider;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import jotalac.market_viewer.market_viewer_app.service.provider.StockDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScreenUpdateService {

    private final AiGenerationProvider aiGenerationProvider;
    private final CryptoDataProvider cryptoDataProvider;
    private final StockDataProvider stockDataProvider;

    public void updateCryptoScreen() {
        return;
    }

    public void updateStockScreen() {
        return;
    }

    public void updateAiTextScreen() {
        return;
    }
}
