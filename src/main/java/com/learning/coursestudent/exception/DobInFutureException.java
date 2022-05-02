package com.learning.coursestudent.exception;

public class DobInFutureException extends AgeException {
    public DobInFutureException(String message) {
        super(message);
    }

    public DobInFutureException(String message, Throwable cause) {
        super(message, cause);
    }
}
