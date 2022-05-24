package com.learning.coursestudent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.DateTimeException;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class DateFormatException extends DateTimeException {
    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
