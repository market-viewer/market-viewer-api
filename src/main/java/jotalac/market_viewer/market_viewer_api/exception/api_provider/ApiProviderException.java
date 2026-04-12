package jotalac.market_viewer.market_viewer_api.exception.api_provider;

public abstract class ApiProviderException extends RuntimeException {
    public ApiProviderException(String message) {
        super(message);
    }
}
