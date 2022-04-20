package com.learning.coursestudent.classes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;

public class CustomException extends Exception {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String StudentTooYoungException(String fullName, long id, short ageLimit) {
        return Student.class.getSimpleName() + " \"" + fullName + "\" with ID \"" + id + "\" cannot be younger than " + ageLimit + "!"
                + System.lineSeparator() + HttpStatus.FORBIDDEN;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String DOBIsInFutureException(String fullName, long id, LocalDate dob) {
        return "Date of Birth \"" + dob + "\" of " + Student.class.getSimpleName() + " \"" + fullName + "\", ID \"" + id + "\", cannot be in the future."
                + System.lineSeparator() + HttpStatus.FORBIDDEN;
    }
}
