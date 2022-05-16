package com.learning.coursestudent.classes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Course extends University {
    //FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
    @SequenceGenerator(name = "course_generator", sequenceName = "course_seq")
    @Column(updatable = false, nullable = false)
    private long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> student;
    @CreationTimestamp
    private LocalDateTime zCreationTime;
    @UpdateTimestamp
    private LocalDateTime zUpdateTime;
    //FIELDS

    //CONSTRUCTORS
    public Course() {
    }

    public Course(CoursePojo coursePojo) {
        this.name = coursePojo.getCourseName();
    }
    //CONSTRUCTORS

    //GETTER AND SETTER
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public LocalDateTime getCreationTime() {
        return zCreationTime;
    }

    public LocalDateTime getUpdateTime() {
        return zUpdateTime;
    }
    //GETTER AND SETTER

}
