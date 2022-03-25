package com.learning.repos;

import com.learning.classes.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Student findByStudentId(long studentId);
    List<Student> findByLastName(String lastName);
    List<Student> findByFirstName(String firstName);

}
