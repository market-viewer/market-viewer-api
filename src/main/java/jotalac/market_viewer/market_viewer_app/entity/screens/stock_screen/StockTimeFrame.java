package jotalac.market_viewer.market_viewer_app.entity.screens.stock_screen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jotalac.market_viewer.market_viewer_app.entity.screens.crypto_screen.CryptoTimeFrame;
import lombok.Getter;

@Getter
public enum StockTimeFrame {
    //stocks trade only 6,5 hours a day, 5 days a week
    FIVE_MINUTES("1min", 5, "5min"),
    HOUR("1min", 60, "1h"),
    FIVE_HOUR("1min", 300, "5h"),
    DAY("5min", 78, "1day"),
    WEEK("30", 65, "7day"),
    TWO_WEEKS("1h", 65, "14day"),
    MONTH("2h", 70, "30day"),
    YEAR("1day", 260, "1y"),
    FIVE_YEARS("1week", 260, "5y");




    private final String interval;
    private final Integer outputsize;
    private final String label;

    StockTimeFrame(String interval, Integer outputsize, String label) {
        this.interval = interval;
        this.outputsize = outputsize;
        this.label = label;
    }

    @JsonCreator
    public static StockTimeFrame fromLabel(String label) {
        for (StockTimeFrame v : StockTimeFrame.values()) {
            if (v.getLabel().equalsIgnoreCase(label)) {
                return v;
            }
        }
        return null;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }


}
