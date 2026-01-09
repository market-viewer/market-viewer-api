package jotalac.market_viewer.market_viewer_app.service.provider;

import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoPriceData;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoTimeFrame;

import java.util.List;

public interface CryptoDataProvider {

    CryptoPriceData fetchCryptoPriceData(String currency, String assetName, CryptoTimeFrame timeFrame, String apiKey);

    List<Double> fetchCryptoGraphData(String something);

    Boolean validateApiKey(String apiKey);

}
