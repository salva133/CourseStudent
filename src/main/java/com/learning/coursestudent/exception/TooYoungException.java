package com.learning.coursestudent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TooYoungException extends AgeException {
    public TooYoungException(String message) {
        super(message);
    }
}
