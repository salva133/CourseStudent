package com.learning.coursestudent.classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.learning.coursestudent.exception.DateFormatException;
import com.learning.coursestudent.exception.DateIsNullException;
import com.learning.coursestudent.exception.TooYoungException;
import com.learning.coursestudent.exception.dobInFutureException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @ManyToOne
    @JsonBackReference
    private Course course;
    @CreationTimestamp
    private LocalDateTime zCreationTime;
    @UpdateTimestamp
    private LocalDateTime zUpdateTime;
    //FIELDS

    //CONSTRUCTORS
    public Student() {
    }

    public Student(StudentPojo studentPojo, short ageLimit, Course course) {
        if (studentPojo.getDateOfBirth() == null) {
            System.out.println("## DATE IS NULL ##");
            System.out.println("Date of birth is null");
            throw new DateIsNullException("Date of birth is null");
        }
        String dobStr = studentPojo.getDateOfBirth();
        if (dobStr.length() != 10) {
            System.out.println("dobStr is not " + 10 + " characters long, I'll try to add \"19\" to it");
            dobStr = "19" + dobStr;
            if (dobStr.length() != 10) {
                System.out.println("## DATE FORMAT INVALID ##");
                System.out.println("dobStr is still not " + 10 + " characters long");
                throw new DateFormatException("Date of birth requires format \"YYYY-MM-DD\"");
            }
        }
        LocalDate dob = LocalDate.parse(dobStr);
        if (dob.isAfter(LocalDate.now())) {
            System.out.println("## DOB IN FUTURE ##");
            System.out.println("dob is after today, and today is " + LocalDate.now());
            throw new dobInFutureException("Date of birth is in the future");
        }
        Period period = Period.between(dob, LocalDate.now());
        this.age = period.getYears();
        if (this.age < ageLimit) {
            System.out.println("## STUDENT IS TOO YOUNG ##");
            System.out.println("Age " + this.age + " is lower than age limit " + ageLimit);
            throw new TooYoungException("Person is too young, limit of Age is " + ageLimit);
        }

        this.firstName = studentPojo.getFirstName();
        this.lastName = studentPojo.getLastName();
        this.fullName = lastName + ", " + firstName;
        this.dateOfBirth = dob;
        this.course = course;
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
        return zCreationTime;
    }

    public LocalDateTime getUpdateTime() {
        return zUpdateTime;
    }
    //GETTER AND SETTER
}