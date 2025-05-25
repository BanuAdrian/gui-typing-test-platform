package exceptions;

public class BadTimerHandlingException extends RuntimeException {
    public BadTimerHandlingException(String message) {
        super(message);
    }
}
