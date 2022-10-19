package com.fatih.exception;

import org.springframework.http.HttpStatus;

public class NoRoomForAnotherPlayerException extends RuntimeException {

    public NoRoomForAnotherPlayerException() {
        super(String.valueOf(HttpStatus.PRECONDITION_FAILED));
    }
}
