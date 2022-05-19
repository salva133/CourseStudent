package com.learning.coursestudent.repository;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findById(long id);

    List<Student> findByLastName(String lastName);

    List<Student> findByFirstName(String firstName);

    List<Student> findByCourse(Course course);
    List<Student> findByCourseName(String courseName);
}