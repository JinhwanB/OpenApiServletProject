package com.example.mission1.exception;

// json 관련 exception
public class NullOfJsonArrayException extends RuntimeException {
    public NullOfJsonArrayException() {
        super();
    }

    public NullOfJsonArrayException(String message) {
        super(message);
    }
}
