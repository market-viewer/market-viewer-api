package jotalac.market_viewer.market_viewer_app.exception.user;

public abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
