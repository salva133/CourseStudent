package com.learning.coursestudent.exception;

import org.hibernate.PropertyValueException;

public class InvalidMailValueException extends PropertyValueException {

    public InvalidMailValueException(String message, String entityName, String propertyName) {
        super(message, entityName, propertyName);
    }
}
