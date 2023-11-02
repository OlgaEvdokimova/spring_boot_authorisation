package com.example.demo.handler;

public final class TokenRefreshNotFoundException extends NotFoundException {

    public TokenRefreshNotFoundException() {
    }

    public TokenRefreshNotFoundException(String message) {
        super(message);
    }

    public TokenRefreshNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenRefreshNotFoundException(Throwable cause) {
        super(cause);
    }

    public TokenRefreshNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
