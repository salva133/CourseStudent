package com.learning.coursestudent.repository;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findById(long id);

    Student findByLastName(String lastName);

    Student findByFirstName(String firstName);

    Student findByCourse(Course course);
}