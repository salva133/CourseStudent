package com.learning.coursestudent.exception;

import java.time.DateTimeException;

public class DateFormatException extends DateTimeException {
    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
