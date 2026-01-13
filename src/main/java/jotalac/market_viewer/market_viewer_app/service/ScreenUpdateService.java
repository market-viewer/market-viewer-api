package jotalac.market_viewer.market_viewer_app.service;

import jotalac.market_viewer.market_viewer_app.dto.api_response.coingecko.CoinGeckoPriceResponse;
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
import java.util.List;

import static jotalac.market_viewer.market_viewer_app.config.Constants.GRAPH_DATA_LIFETIME_MINUTES;

@Service
@RequiredArgsConstructor
public class ScreenUpdateService {

    private final AiGenerationProvider aiGenerationProvider;
    private final CryptoDataProvider cryptoDataProvider;
    private final StockDataProvider stockDataProvider;
    private final ApiKeyRepository apiKeyRepository;


    public void updateCryptoScreen(CryptoScreen cryptoScreen) {
        ApiKey userApiKey = apiKeyRepository.findByEndpointAndUser(ApiKeyProvider.COINGECKO, cryptoScreen.getDevice().getUser())
                .orElseThrow(() -> new MissingApiKey("Missing api key"));

        CoinGeckoPriceResponse newData = cryptoDataProvider.fetchCryptoPriceData(
                cryptoScreen.getCurrency(), cryptoScreen.getAssetName(), cryptoScreen.getTimeFrame(), userApiKey.getValue()
        );

        //set the data
        cryptoScreen.getPriceData().setFetchTimePrice(LocalDateTime.now());
        cryptoScreen.getPriceData().setPrice(newData.getPrice());
        cryptoScreen.getPriceData().setPriceChange(newData.getPriceChange());
        cryptoScreen.getPriceData().setAllTimeHigh(newData.getAllTimeHigh());
        cryptoScreen.getPriceData().setAllTimeHighChange(newData.getAllTimeHighChange());

        //check if we need to fetch graph data
        LocalDateTime lastGraphFetchTime = cryptoScreen.getPriceData().getFetchTimeGraph();
        if (
                lastGraphFetchTime == null ||
                (cryptoScreen.getPriceData().getFetchTimeGraph()
                    .plusMinutes(GRAPH_DATA_LIFETIME_MINUTES)
                    .isBefore(LocalDateTime.now()) &&
                cryptoScreen.getDisplayGraph())
        )
        {
            List<Double> newGraphData = cryptoDataProvider.fetchCryptoGraphData(
                    cryptoScreen.getCurrency(), cryptoScreen.getAssetName(), cryptoScreen.getTimeFrame(), userApiKey.getValue()
            );

            cryptoScreen.getPriceData().setGraphData(newGraphData);
            cryptoScreen.getPriceData().setFetchTimeGraph(LocalDateTime.now());
        }

    }

    public void updateStockScreen(StockScreen stockScreen) {
//        verifyUserHasApiKey(stockScreen, ApiKeyProvider.FINNHUB);

        return;
    }

    public void updateAiTextScreen(AITextScreen aiTextScreen) {
        return;
    }
}
