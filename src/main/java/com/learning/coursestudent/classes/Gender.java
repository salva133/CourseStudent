package com.learning.coursestudent.classes;

import org.hibernate.PropertyValueException;

public enum Gender {
    MALE, FEMALE;

    public String getGender() throws NullPointerException, PropertyValueException {
        return switch (this) {
            case MALE -> "Male";
            case FEMALE -> "Female";
        };
    }
}
