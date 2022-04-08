package com.learning.coursestudent.repos;

import com.learning.coursestudent.classes.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student getByStudentId(long studentId);
    Student getByLastName(String lastName);
    Student getByFirstName(String firstName);
}