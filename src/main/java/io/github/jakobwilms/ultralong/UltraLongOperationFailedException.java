package io.github.jakobwilms.ultralong;

public class UltraLongOperationFailedException extends RuntimeException {
    public UltraLongOperationFailedException() {
        super();
    }

    public UltraLongOperationFailedException(String message) {
        super(message);
    }

    public UltraLongOperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
