package com.learning.coursestudent.classes;

import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_generator")
    @SequenceGenerator(name = "student_generator", sequenceName = "student_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private long studentId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    public Student() {
    }

    public Student(String firstName, String lastName, Course course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }

    public Student(long studentId, String firstName, String lastName, Course course) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}