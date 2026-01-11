package jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

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

    @JsonValue
    public String getValue() { return value;}

    // this handles the incoming values
    @JsonCreator
    public static CryptoTimeFrame fromValue(String value) {
        for (CryptoTimeFrame v : CryptoTimeFrame.values()) {
            if (v.getValue().equalsIgnoreCase(value) || v.name().equalsIgnoreCase(value)) {
                return v;
            }
        }
        return null;
    }
}
