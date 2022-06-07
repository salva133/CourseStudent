package com.learning.coursestudent.classes;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Course extends University {
    //FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
    @SequenceGenerator(name = "course_generator", sequenceName = "course_seq")
    @Column(updatable = false, nullable = false)
    private long id;
    @Column(nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(name = "course_student", joinColumns = {@JoinColumn(name = "fk_course")},
            inverseJoinColumns = {@JoinColumn(name = "fk_student")})
    private Set<Student> student = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime zCreationTime;
    @UpdateTimestamp
    private LocalDateTime zUpdateTime;
    //FIELDS

    //CONSTRUCTORS
    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Course(CoursePojo coursePojo) {
        this(coursePojo.getCourseName());
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

    public Set<Student> getStudent() {
        return student;
    }

    public void setStudent(Set<Student> student) {
        this.student = student;
    }

    public LocalDateTime getCreationTime() {
        return zCreationTime;
    }

    public LocalDateTime getUpdateTime() {
        return zUpdateTime;
    }
    //GETTER AND SETTER


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(student, course.student) && Objects.equals(zCreationTime, course.zCreationTime) && Objects.equals(zUpdateTime, course.zUpdateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, student, zCreationTime, zUpdateTime);
    }
}
