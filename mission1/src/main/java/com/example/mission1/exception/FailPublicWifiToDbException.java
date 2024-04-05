package com.example.mission1.exception;

// 공공 와이파이 정보를 db에 저장 실패 시 exception
public class FailPublicWifiToDbException extends RuntimeException {
    public FailPublicWifiToDbException() {
        super();
    }

    public FailPublicWifiToDbException(String message) {
        super(message);
    }
}
