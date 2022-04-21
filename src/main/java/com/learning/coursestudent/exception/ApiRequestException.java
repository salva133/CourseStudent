package com.learning.coursestudent.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ApiRequestException extends DataIntegrityViolationException {

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
