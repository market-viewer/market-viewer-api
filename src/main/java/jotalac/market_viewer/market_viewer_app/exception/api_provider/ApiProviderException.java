package jotalac.market_viewer.market_viewer_app.exception.api_provider;

public abstract class ApiProviderException extends RuntimeException {
    public ApiProviderException(String message) {
        super(message);
    }
}
