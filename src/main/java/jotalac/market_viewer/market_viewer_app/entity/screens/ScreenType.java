package jotalac.market_viewer.market_viewer_app.entity.screens;

public enum ScreenType {
    CRYPTO,
    STOCK,
    CLOCK,
    AI_TEXT;

    public static ScreenType fromString(String type) {
        if (type == null) {
            return null;
        }
        try {
            return ScreenType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid screen type: " + type);
        }
    }
}
