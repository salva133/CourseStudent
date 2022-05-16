package com.learning.coursestudent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class AgeException extends NotCreatedException {
    public AgeException(String s) {
        super(s);
    }
}
