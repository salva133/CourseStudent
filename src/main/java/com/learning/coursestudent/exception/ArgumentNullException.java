package com.learning.coursestudent.exception;

public class ArgumentNullException extends NullPointerException {
    public ArgumentNullException() {
    }

    public ArgumentNullException(String s) {
        super(s);
    }
}
