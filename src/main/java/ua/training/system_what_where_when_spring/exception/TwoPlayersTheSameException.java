package ua.training.system_what_where_when_spring.exception;

public class TwoPlayersTheSameException extends RuntimeException {
    public TwoPlayersTheSameException(String message) {
        super(message);
    }
}
