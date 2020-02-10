package ua.training.game.exception;

public class TwoPlayersTheSameException extends RuntimeException {
    public TwoPlayersTheSameException(String message) {
        super(message);
    }
}
