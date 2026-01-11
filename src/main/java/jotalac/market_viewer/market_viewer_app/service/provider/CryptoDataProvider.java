package jotalac.market_viewer.market_viewer_app.service.provider;

import jotalac.market_viewer.market_viewer_app.dto.api_response.coingecko.CoinGeckoPriceResponse;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoPriceData;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoTimeFrame;

import java.util.List;

public interface CryptoDataProvider {

    CoinGeckoPriceResponse fetchCryptoPriceData(String currency, String assetName, CryptoTimeFrame timeFrame, String apiKey);

    List<Double> fetchCryptoGraphData(String something);

    void validateApiKey(String apiKey);

    void validateCoinName(String assetName, String apiKey);

}
