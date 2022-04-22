package com.learning.coursestudent.classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.learning.coursestudent.exception.AgeException;
import com.learning.coursestudent.exception.DateIsNullException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
public class Student {
    //FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
    @SequenceGenerator(name = "student_generator", sequenceName = "student_seq")
    @Column(updatable = false, nullable = false)
    private long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private LocalDate dateOfBirth;
    private int age;
    @CreationTimestamp
    private LocalDateTime creationTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
    @ManyToOne
    @JsonBackReference
    private Course course;
    //FIELDS

    //CONSTRUCTORS
    public Student() {
    }
/*
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = lastName + ", " + firstName;
    }
 */

    public Student(StudentPojo studentPojo, short ageLimit) throws AgeException {
        if (studentPojo.getDateOfBirth() == null) {
            throw new DateIsNullException("Date of Birth is null");
        }
        LocalDate dob = LocalDate.parse(studentPojo.getDateOfBirth());
        if (dob.isAfter(LocalDate.now())) {
            throw new AgeException("Date of Birth is in the Future");
        }
        Period period = Period.between(dob, LocalDate.now());
        this.age = period.getYears();
        if (this.age < ageLimit) {
            throw new AgeException("Person is too young, Limit of Age is " + ageLimit);
        }

        this.firstName = studentPojo.getFirstName();
        this.lastName = studentPojo.getLastName();
        this.fullName = lastName + ", " + firstName;
        this.dateOfBirth = dob;

    }
    //CONSTRUCTORS

    //GETTER AND SETTER
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

    public String getFullName() {
        return fullName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    //GETTER AND SETTER

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String StudentTooYoungValidation(String fullName, short ageLimit) {
        return Student.class.getSimpleName() + " \"" + fullName + "\" cannot be younger than " + ageLimit + "!";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String dobIsInFutureValidation(String fullName, LocalDate dob) {
        return "Date of Birth \"" + dob + "\" of " + Student.class.getSimpleName() + " \"" + fullName + "\", cannot be in the future.";
    }
    //MISC CLASS METHODS
}