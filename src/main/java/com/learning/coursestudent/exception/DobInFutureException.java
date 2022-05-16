package com.learning.coursestudent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DobInFutureException extends AgeException {
    public DobInFutureException(String s) {
        super(s);
    }
}
