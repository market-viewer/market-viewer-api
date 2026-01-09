package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoPriceData;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoTimeFrame;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CoinGeckoCryptoProvider implements CryptoDataProvider {
    CoinGeckoCryptoProvider(@Qualifier("finhubClient") RestClient restClient) {this.restClient = restClient;}

    private final RestClient restClient;

    @Override
    public CryptoPriceData fetchCryptoPriceData(String currency, String assetName, CryptoTimeFrame timeFrame, String apiKey) {
        String response = restClient.get()
                .uri(String.format(
                        "coins/markets?vs_currency=%s&ids=%s&price_change_percentage=%s",
                        currency, assetName, timeFrame.getValue()
                        )
                )
                .header("x-cg-demo-api-key", apiKey)
                .retrieve()
                .toString();

        System.out.println(response);
        return null;
    }

    @Override
    public List<Double> fetchCryptoGraphData(String something) {
        return List.of();
    }

    @Override
    public Boolean validateApiKey(String apiKey) {
        return null;
    }
}
