package jotalac.market_viewer.market_viewer_app.service.provider;

import jotalac.market_viewer.market_viewer_app.entity.screens.CryptoPriceData;

public interface CryptoDataProvider {

    CryptoPriceData fetchCryptoPriceData(String coinName);
}
