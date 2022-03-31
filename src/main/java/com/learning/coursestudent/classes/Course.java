package com.learning.coursestudent.classes;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_generator")
    @SequenceGenerator(name = "course_generator", sequenceName = "course_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private long courseId;
    @Column(name = "course_name")
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Student> student;

    public Course() {
    }

    public Course(long courseId, String courseName,List<Student> student) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.student = student;
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
