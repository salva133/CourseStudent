package com.learning.coursestudent.exception;

public class NameExpectedException extends ApiRequestException {
    public NameExpectedException(String message) {
        super(message);
    }

    public NameExpectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
