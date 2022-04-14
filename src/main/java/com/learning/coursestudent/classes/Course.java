package com.learning.coursestudent.classes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.Gson;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_generator")
    @SequenceGenerator(name = "course_generator", sequenceName = "course_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "course_name")
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> student;

    public Course() {
    }

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public Course(String courseName, List<Student> student) {
        this.courseName = courseName;
        this.student = student;
    }

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

    public String toJson(Course course) {
        Gson gson = new Gson();
        return gson.toJson(course);
    }
}
