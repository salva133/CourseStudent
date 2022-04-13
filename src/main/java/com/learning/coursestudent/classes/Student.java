package com.learning.coursestudent.classes;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_generator")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

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

    public long getId() {
        return id;
    }

    public void setId(long studentId) {
        this.id = studentId;
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

    public void AgeAbove100Check() {
        if (age > 100) {
            System.out.println("##### Age of \""+fullName+"\" is above 100 years. Make sure that the date of birth given is correct. If this value is intended, you can ignore this message #####");
        }
    }
}