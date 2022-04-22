package com.learning.coursestudent.exception;

public class DateFormatException extends RuntimeException {
    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
