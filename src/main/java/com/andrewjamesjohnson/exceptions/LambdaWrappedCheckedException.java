package com.andrewjamesjohnson.exceptions;

/**
 * Unchecked exception used to wrap checked exceptions thrown inside of a lambda expression
 */
public class LambdaWrappedCheckedException extends RuntimeException {
    public LambdaWrappedCheckedException() {
    }

    public LambdaWrappedCheckedException(String message) {
        super(message);
    }

    public LambdaWrappedCheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LambdaWrappedCheckedException(Throwable cause) {
        super(cause);
    }

    public LambdaWrappedCheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
