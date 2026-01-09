package jotalac.market_viewer.market_viewer_app.service;

import jotalac.market_viewer.market_viewer_app.entity.ApiKey;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyProvider;
import jotalac.market_viewer.market_viewer_app.entity.screens.AITextScreen;
import jotalac.market_viewer.market_viewer_app.entity.screens.Screen;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoPriceData;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoScreen;
import jotalac.market_viewer.market_viewer_app.entity.screens.StockScreen;
import jotalac.market_viewer.market_viewer_app.exception.user.MissingApiKey;
import jotalac.market_viewer.market_viewer_app.repository.ApiKeyRepository;
import jotalac.market_viewer.market_viewer_app.service.provider.AiGenerationProvider;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import jotalac.market_viewer.market_viewer_app.service.provider.StockDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static jotalac.market_viewer.market_viewer_app.config.Constants.GRAPH_DATA_LIFETIME_MINUTES;

@Service
@RequiredArgsConstructor
public class ScreenUpdateService {

    private final AiGenerationProvider aiGenerationProvider;
    private final CryptoDataProvider cryptoDataProvider;
    private final StockDataProvider stockDataProvider;
    private final ApiKeyRepository apiKeyRepository;


    public void updateCryptoScreen(CryptoScreen cryptoScreen) {
        verifyUserHasApiKey(cryptoScreen, ApiKeyProvider.COINGECKO);
        ApiKey userApiKey = apiKeyRepository.findByEndpointAndUser(ApiKeyProvider.COINGECKO, cryptoScreen.getDevice().getUser())
                .orElseThrow(() -> new MissingApiKey("Missing api key"));

        CryptoPriceData newData = cryptoDataProvider.fetchCryptoPriceData(
                cryptoScreen.getCurrency(), cryptoScreen.getAssetName(), cryptoScreen.getTimeFrame(), userApiKey.getValue()
        );
        //check if we need to fetch graph data
        if (cryptoScreen.getPriceData().getFetchTimeGraph()
                .plusMinutes(GRAPH_DATA_LIFETIME_MINUTES)
                .isBefore(LocalDateTime.now()) && cryptoScreen.getDisplayGraph())
        {
            newData.setGraphData(cryptoDataProvider.fetchCryptoGraphData("yoyoy"));
        }

        //set the data
    }

    public void updateStockScreen(StockScreen stockScreen) {
        verifyUserHasApiKey(stockScreen, ApiKeyProvider.FINNHUB);

        return;
    }

    public void updateAiTextScreen(AITextScreen aiTextScreen) {
        return;
    }
}
