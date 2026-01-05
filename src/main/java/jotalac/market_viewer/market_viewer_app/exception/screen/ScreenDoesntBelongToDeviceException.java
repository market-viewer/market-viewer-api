package jotalac.market_viewer.market_viewer_app.exception.screen;

public class ScreenDoesntBelongToDeviceException extends RuntimeException {
    public ScreenDoesntBelongToDeviceException(String message) {
        super(message);
    }
}
