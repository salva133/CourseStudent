package com.learning.coursestudent.exception;

import java.time.DateTimeException;

public class DateIsNullException extends DateTimeException {
    public DateIsNullException(String message) {
        super(message);
    }

    public DateIsNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
