package com.insignia.jnguyen.robotchallenge.exception;

public class LoadException extends RuntimeException {
    public LoadException(String message, Exception exception) {
        super(message, exception);
    }
}
