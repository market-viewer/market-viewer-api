package jotalac.market_viewer.market_viewer_api.exception.device;

public abstract class DeviceException extends RuntimeException {
    public DeviceException(String message) {
        super(message);
    }
}
