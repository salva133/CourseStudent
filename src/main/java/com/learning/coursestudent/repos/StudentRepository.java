package com.learning.coursestudent.repos;

import com.learning.coursestudent.classes.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findAll();
    Student findByStudentId(long studentId);
    List<Student> findByLastName(String lastName);
    List<Student> findByFirstName(String firstName);

}