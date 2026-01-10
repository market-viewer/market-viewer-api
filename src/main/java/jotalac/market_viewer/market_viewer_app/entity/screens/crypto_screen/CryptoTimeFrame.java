package jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen;

import lombok.Getter;

@Getter
public enum CryptoTimeFrame {
    HOUR("1h"),
    DAY("24h"),
    WEEK("7d"),
    TWO_WEEKS("14d"),
    MONTH("30d"),
    TWO_HUNDRED_DAYS("200d"),
    YEAR("1y");

    private final String value;

    CryptoTimeFrame(String value) {
        this.value = value;
    }

}
