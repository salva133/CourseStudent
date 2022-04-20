package com.learning.coursestudent.classes;

public class StudentPojo {
    String firstName;
    String lastName;
    String dateOfBirth;

    public StudentPojo(String firstName, String lastName, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

}
