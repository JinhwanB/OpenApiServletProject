package com.example.mission1.exception;

// pk관련 exception
public class PrimaryKeyException extends RuntimeException {
    public PrimaryKeyException() {
        super();
    }

    public PrimaryKeyException(String message) {
        super(message);
    }
}
