package com.learning.coursestudent.classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "age")
    private int age;
    @Column(name = "creation_time")
    @CreationTimestamp
    private LocalDateTime creationTime;
    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "course_id")
    private Course course;
    //FIELDS

    //CONSTRUCTORS
    public Student() {
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = lastName + ", " + firstName;
    }

    public Student(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = lastName + ", " + firstName;
        this.dateOfBirth = dateOfBirth;
        Period period = Period.between(dateOfBirth, LocalDate.now());
        this.age = period.getYears();
        AgeAbove100Check();
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

    //MISC CLASS METHODS
    public void AgeAbove100Check() {
        if (age > 100) {
            System.out.println("##### Age of \"" + fullName + "\" is above 100 years. Make sure that the date of birth given is correct. If this value is intended, you can ignore this message #####");
        }
    }
    //MISC CLASS METHODS
}