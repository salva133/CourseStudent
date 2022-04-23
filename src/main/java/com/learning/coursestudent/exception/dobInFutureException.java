package com.learning.coursestudent.exception;

public class dobInFutureException extends AgeException {
    public dobInFutureException(String message) {
        super(message);
    }

    public dobInFutureException(String message, Throwable cause) {
        super(message, cause);
    }
}
