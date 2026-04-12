package jotalac.market_viewer.market_viewer_api.exception.user;

public abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
