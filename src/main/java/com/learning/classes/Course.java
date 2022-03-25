package com.learning.classes;

import javax.persistence.*;
import java.util.Iterator;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Course_Id")
    private long courseId;
    @Column(name = "Course_Name")
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.PERSIST)
    private List<Student> student;

    public Course() {
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
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
}
