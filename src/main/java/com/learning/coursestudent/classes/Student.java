package com.learning.coursestudent.classes;

import com.learning.coursestudent.exception.DateFormatException;
import com.learning.coursestudent.exception.DobInFutureException;
import com.learning.coursestudent.exception.NullDateException;
import com.learning.coursestudent.exception.TooYoungException;
import com.learning.coursestudent.service.StudentService;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity

public class Student extends University {
    final static org.jboss.logging.Logger logger = Logger.getLogger(StudentService.class);

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
    private Gender gender;
    @ManyToOne
    private Course course;
    @CreationTimestamp
    private LocalDateTime zCreationTime;
    @UpdateTimestamp
    private LocalDateTime zUpdateTime;
    //FIELDS

    //CONSTRUCTORS
    public Student() {
    }
    public Student(String lastName) {
        this.lastName = lastName;
    }

    public Student(StudentPojo studentPojo, short ageLimit, Course course) {
        if (studentPojo.getDateOfBirth() == null) {
            logger.debug("## DATE IS NULL ##");
            logger.debug("Date of birth is null");
            throw new NullDateException("Date of birth is null");
        }
        String dobStr = studentPojo.getDateOfBirth();
        if (dobStr.length() != 10) {
            logger.debug("dobStr is not " + 10 + " characters long" +
                    System.lineSeparator() + "SELF TREATMENT: trying to add \"19\" to it");
            dobStr = "19" + dobStr;
            if (dobStr.length() != 10) {
                logger.debug("## DATE FORMAT INVALID ##");
                logger.debug("dobStr is still not " + 10 + " characters long");
                throw new DateFormatException("Date of birth requires format \"YYYY-MM-DD\"");
            }
        }
        LocalDate dob = LocalDate.parse(dobStr);
        if (dob.isAfter(LocalDate.now())) {
            logger.debug("## DOB IN FUTURE ##");
            logger.debug("dob is after today, and today is " + LocalDate.now());
            throw new DobInFutureException("Date of birth is in the future");
        }
        Period period = Period.between(dob, LocalDate.now());
        this.age = period.getYears();
        if (this.age < ageLimit) {
            logger.debug("## STUDENT IS TOO YOUNG ##");
            logger.debug("Age " + this.age + " is lower than age limit " + ageLimit);
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDateTime getzCreationTime() {
        return zCreationTime;
    }

    public LocalDateTime getzUpdateTime() {
        return zUpdateTime;
    }

    //GETTER AND SETTER
}