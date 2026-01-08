package jotalac.market_viewer.market_viewer_app.service.provider.impl;

import jotalac.market_viewer.market_viewer_app.entity.screens.CryptoPriceData;
import jotalac.market_viewer.market_viewer_app.service.provider.CryptoDataProvider;
import org.springframework.stereotype.Service;

@Service
public class CoinGeckoCryptoProvider implements CryptoDataProvider {

    @Override
    public CryptoPriceData fetchCryptoPriceData(String coinName) {
        return null;
    }
}
