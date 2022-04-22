package com.learning.coursestudent.exception;

public class AgeException extends NotCreatedException {

    public AgeException(String message) {
        super(message);
    }

    public AgeException(String message, Throwable cause) {
        super(message, cause);
    }
}
