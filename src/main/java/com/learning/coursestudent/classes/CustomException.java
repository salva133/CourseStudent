package com.learning.coursestudent.classes;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class CustomException extends Exception {

    public String StudentTooYoungException(String fullName, long id, short ageLimit) {
        return Student.class.getSimpleName()+" \""+fullName+"\" with ID \""+id+"\" cannot be younger than "+ageLimit+"!"
                +System.lineSeparator()+HttpStatus.FORBIDDEN;
    }

    public String DOBIsInFutureException(String fullName, LocalDate dob) {
        return "Date of Birth \""+dob+"\" of "+Student.class.getSimpleName()+" \""+fullName+"\" cannot be in the future."
                +System.lineSeparator()+HttpStatus.FORBIDDEN;
    }
}
