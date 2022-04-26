package com.learning.coursestudent.classes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.learning.coursestudent.exception.NameExpectedException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Course {
    //FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
    @SequenceGenerator(name = "course_generator", sequenceName = "course_seq")
    @Column(updatable = false, nullable = false)
    private long id;
    @Column(nullable = false)
    private String courseName;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> student;
    @CreationTimestamp
    private LocalDateTime creationTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
    //FIELDS

    //CONSTRUCTORS
    public Course() {
    }

    public Course(CoursePojo coursePojo) {
        if (coursePojo.getCourseName() == null || coursePojo.getCourseName().equals("")) {
            System.out.println("## NAME IS EMPTY OR NULL ##");
            throw new NameExpectedException("A Name was expected");
        }
        this.courseName = coursePojo.getCourseName();
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

}
