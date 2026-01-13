package jotalac.market_viewer.market_viewer_app.config;

public final class Constants {
    private Constants() {}

    // user data validation
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 50;

    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 256;

    // device
    public static final int DEVICE_NAME_MIN_LENGTH = 3;
    public static final int DEVICE_NAME_MAX_LENGTH = 60;
    public static final int DEVICE_MAX_SCREENS = 5;

    public static final String DEFAULT_BG_COLOR = "0x23f4";

    // hardware data fetch
    public static final Integer PRICE_DATA_LIFETIME_MINUTES = 3;
    public static final Integer GRAPH_DATA_LIFETIME_MINUTES = 30;

    public static final Integer PRICE_GRAPH_POINTS = 90;

}
