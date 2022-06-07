package com.learning.coursestudent.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class StudentResponse {
    long id;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String courseName;

    public StudentResponse() {
    }

    public StudentResponse(long id, String firstName, String lastName, LocalDate dateOfBirth, String courseName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.courseName = courseName;
    }

    public StudentResponse(Student student) {
        this(student.getId(), student.getFirstName(), student.getLastName(), student.getDateOfBirth(), String.valueOf(new ArrayList<>(student.getCourse())));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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
        StudentResponse that = (StudentResponse) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName) && dateOfBirth.equals(that.dateOfBirth) && courseName.equals(that.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, courseName);
    }
}
