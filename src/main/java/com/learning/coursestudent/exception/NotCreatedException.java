package com.learning.coursestudent.exception;

public class NotCreatedException extends Exception {
    public NotCreatedException() {
    }

    public NotCreatedException(String message) {
        super(message);
    }

    public NotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
