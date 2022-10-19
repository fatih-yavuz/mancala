package com.fatih.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(final String message) {
        super(message);
    }
}
