package jotalac.market_viewer.market_viewer_app.exception.api_provider;

public class ApiRateLimitExceededException extends RuntimeException {
    public ApiRateLimitExceededException(String message) {
        super(message);
    }
}
