package com.learning.coursestudent.classes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.Gson;
import jdk.jfr.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Course {
    //FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_generator")
    @SequenceGenerator(name = "course_generator", sequenceName = "course_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "course_name", nullable = false)
    private String courseName;
    @Column(name = "creation_time")
    @CreationTimestamp
    private LocalDateTime creationTime;
    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> student;
    //FIELDS

    //CONSTRUCTORS
    public Course() {
    }

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public Course(String courseName, List<Student> student) {
        this.courseName = courseName;
        this.student = student;
    }
    //CONSTRUCTORS

    //GETTER AND SETTER
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    //GETTER AND SETTER

    //MISC CLASS METHODS
    public String toJson(Course course) {
        Gson gson = new Gson();
        return gson.toJson(course);
    }
    //MISC CLASS METHODS
}
