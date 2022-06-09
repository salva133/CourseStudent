package com.learning.coursestudent.exception;

import javax.persistence.PersistenceException;

public class DuplicateObjectException extends PersistenceException {
    public DuplicateObjectException() {
    }

    public DuplicateObjectException(String message) {
        super(message);
    }

    public DuplicateObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateObjectException(Throwable cause) {
        super(cause);
    }
}
