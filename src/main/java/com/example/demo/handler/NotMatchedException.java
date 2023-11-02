package com.example.demo.handler;

public class NotMatchedException extends RuntimeException {
    public NotMatchedException() {
    }

    public NotMatchedException(String message) {
        super(message);
    }

    public NotMatchedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotMatchedException(Throwable cause) {
        super(cause);
    }

    public NotMatchedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
