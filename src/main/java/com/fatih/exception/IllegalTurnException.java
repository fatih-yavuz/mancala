package com.fatih.exception;

public class IllegalTurnException extends RuntimeException {
    public IllegalTurnException(final String message) {
        super(message);
    }
}
