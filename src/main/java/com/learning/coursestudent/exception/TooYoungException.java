package com.learning.coursestudent.exception;

public class TooYoungException extends AgeException {
    public TooYoungException(String message) {
        super(message);
    }

    public TooYoungException(String message, Throwable cause) {
        super(message, cause);
    }
}
