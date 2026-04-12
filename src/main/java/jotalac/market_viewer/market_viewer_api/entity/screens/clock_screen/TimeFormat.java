package jotalac.market_viewer.market_viewer_api.entity.screens.clock_screen;

public enum TimeFormat {
    TWELVE_HOUR("12h"),
    TWENTY_FOUR_HOUR("24h");

    private final String displayValue;
    TimeFormat(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
