package jotalac.market_viewer.market_viewer_app.entity.screens;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum AssetTimeFrame {
    FIVE_MINUTES("5min"),
    HOUR("1h"),
    FIVE_HOUR("5h"),
    DAY("24h"),
    WEEK("7day"),
    TWO_WEEKS("14day"),
    MONTH("30day"),
    TWO_HUNDRED_DAYS("200day"),
    YEAR("1year"),
    FIVE_YEARS("5year");

    private final String label;

    AssetTimeFrame(String label) {
        this.label = label;
    }

    @JsonValue
    public String toJson() {
        return label;
    }

    @JsonCreator
    public static AssetTimeFrame fromJson(String value) {
        for (AssetTimeFrame timeFrame : values()) {
            if (timeFrame.label.equalsIgnoreCase(value)) return timeFrame;
        }
        throw new IllegalArgumentException("Unsupported timeframe: " + value);
    }
}
