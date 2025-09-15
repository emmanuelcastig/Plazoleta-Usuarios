package co.com.pragma.usecase.exception;

public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException(String message) {
        super(message);
    }
}