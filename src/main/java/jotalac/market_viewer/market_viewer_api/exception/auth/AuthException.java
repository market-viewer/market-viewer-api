package jotalac.market_viewer.market_viewer_api.exception.auth;

public abstract class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
