package ua.training.game.exception;

public class AccessNotAllowedException extends RuntimeException {
    public AccessNotAllowedException(String message) {
        super(message);
    }
}