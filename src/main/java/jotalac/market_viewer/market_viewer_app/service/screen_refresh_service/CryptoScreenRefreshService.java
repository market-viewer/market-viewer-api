package jotalac.market_viewer.market_viewer_app.service.screen_refresh_service;

import jotalac.market_viewer.market_viewer_app.dto.api_response.crypto_api.CryptoPriceResponse;
import jotalac.market_viewer.market_viewer_app.entity.ApiKey;
import jotalac.market_viewer.market_viewer_app.entity.ApiKeyProvider;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoScreen;
import jotalac.market_viewer.market_viewer_app.exception.user.MissingApiKey;
import jotalac.market_viewer.market_viewer_app.repository.ApiKeyRepository;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static jotalac.market_viewer.market_viewer_app.config.Constants.GRAPH_DATA_LIFETIME_MINUTES;


@Service
@RequiredArgsConstructor
public class CryptoScreenRefreshService {

    private final CryptoDataProvider cryptoDataProvider;
    private final ApiKeyRepository apiKeyRepository;


    public void refreshCryptoScreen(CryptoScreen cryptoScreen) {
        ApiKey userApiKey = apiKeyRepository.findByEndpointAndUser(ApiKeyProvider.COINGECKO, cryptoScreen.getDevice().getUser())
                .orElseThrow(() -> new MissingApiKey("Missing api key"));

        updatePrice(cryptoScreen, userApiKey.getValue());

        //graph data are not fetched every time
        updateGraphData(cryptoScreen, userApiKey.getValue());
    }

    private void updatePrice(CryptoScreen cryptoScreen, String apiKey) {
        CryptoPriceResponse newData = cryptoDataProvider.fetchCryptoPriceData(
                cryptoScreen.getCurrency(), cryptoScreen.getAssetName(), cryptoScreen.getTimeFrame(), apiKey
        );

        //set the data
        var cryptoScreenPriceData = cryptoScreen.getPriceData();
        cryptoScreenPriceData.setFetchTimePrice(LocalDateTime.now());
        cryptoScreenPriceData.setPrice(newData.getPrice());
        cryptoScreenPriceData.setPriceChange(newData.getPriceChange());
        cryptoScreenPriceData.setAllTimeHigh(newData.getAllTimeHigh());
        cryptoScreenPriceData.setAllTimeHighChange(newData.getAllTimeHighChange());
    }

    private void updateGraphData(CryptoScreen cryptoScreen, String apiKey) {
        var cryptoScreenPriceData = cryptoScreen.getPriceData();
        //check if we need to fetch graph data
        LocalDateTime lastGraphFetchTime = cryptoScreenPriceData.getFetchTimeGraph();
        if (
                cryptoScreen.getDisplayGraph() &&
                (
                    lastGraphFetchTime == null || cryptoScreenPriceData.getFetchTimeGraph()
                        .plusMinutes(GRAPH_DATA_LIFETIME_MINUTES)
                        .isBefore(LocalDateTime.now())
                )
        )
        {
            List<Double> newGraphData = cryptoDataProvider.fetchCryptoGraphData(
                    cryptoScreen.getCurrency(), cryptoScreen.getAssetName(), cryptoScreen.getTimeFrame(), apiKey
            );

            cryptoScreenPriceData.setGraphData(newGraphData);
            cryptoScreenPriceData.setFetchTimeGraph(LocalDateTime.now());
        }
    }

}
