package com.learning.coursestudent.classes;

import com.learning.coursestudent.exception.*;
import com.learning.coursestudent.service.StudentService;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Set;

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
    private String mail;
    private LocalDate dateOfBirth;
    private int age;
    private Gender gender;
    @ManyToMany
    @JoinTable(name = "student_course", joinColumns = {@JoinColumn(name = "fk_student")},
            inverseJoinColumns = {@JoinColumn(name = "fk_course")})
    private Set<Course> course = new HashSet<>();
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

    public Student(String lastName, Set<Course> course) {
        this.lastName = lastName;
        this.course = course;
    }

    public Student(StudentPojo studentPojo, short ageLimit, Set<Course> course) {
        //MAIL
        if (studentPojo.getMail() == null || studentPojo.getMail().isBlank()) {
            String firstLetterOfFirstNameLowerCase = String.valueOf(studentPojo.getFirstName().charAt(0));
            this.mail = firstLetterOfFirstNameLowerCase.concat("." + studentPojo.getLastName().toLowerCase()).concat("@mydomain.com");
        } else if (!studentPojo.getMail().contains("@")) {
            throw new InvalidMailValueException("Mail Address must contain the '@'", "Record.Value -> " + studentPojo.getFirstName() + ", " + studentPojo.getLastName(), studentPojo.getMail());
        } else {
            this.mail = studentPojo.getMail();
        }

        //DATE OF BIRTH
        if (studentPojo.getDateOfBirth() == null) {
            throw new NullDateException("Date of birth is null");
        }
        String dobStr = studentPojo.getDateOfBirth();
        if (dobStr.length() != 10) {
            logger.warn("dobStr is not " + 10 + " characters long but " + dobStr.length() + ", SELF TREATMENT: trying to add \"19\" to it");
            dobStr = "19" + dobStr;
            if (dobStr.length() != 10) {
                logger.error("dobStr is still not " + 10 + " characters long. Operation will be interrupted...");
                throw new DateFormatException("Date of birth requires format \"YYYY-MM-DD\"");
            }
        }
        LocalDate dob = LocalDate.parse(dobStr);
        if (dob.isAfter(LocalDate.now())) {
            throw new DobInFutureException("Date of birth is in the future");
        }
        Period period = Period.between(dob, LocalDate.now());
        this.age = period.getYears();
        if (this.age < ageLimit) {
            throw new TooYoungException("Person is too young, limit of Age is " + ageLimit);
        }
        //GENDER
        switch (studentPojo.getGender().toLowerCase()) {
            case "male" -> this.setGender(Gender.MALE);
            case "female" -> this.setGender(Gender.FEMALE);
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

    public Set<Course> getCourse() {
        return course;
    }

    public void setCourse(Set<Course> course) {
        if (course != null) {
            this.course = course;
        } else {
            throw new MissingResourceException("Course is null", "Course", getFullName());
        }
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDateTime getzCreationTime() {
        return zCreationTime;
    }

    public LocalDateTime getzUpdateTime() {
        return zUpdateTime;
    }

    //GETTER AND SETTER
}