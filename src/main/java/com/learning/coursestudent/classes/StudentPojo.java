package com.learning.coursestudent.classes;

import java.util.Objects;

public class StudentPojo {
    String firstName;
    String lastName;
    String dateOfBirth;
    String courseName;

    public StudentPojo() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "Name = \"" + lastName + ", " + firstName + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentPojo that = (StudentPojo) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName) && dateOfBirth.equals(that.dateOfBirth) && courseName.equals(that.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, courseName);
    }
}
