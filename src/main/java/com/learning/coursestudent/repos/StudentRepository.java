package com.learning.coursestudent.repos;

import com.learning.coursestudent.classes.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Student getByStudentId(long studentId);
    Student getByLastName(String lastName);
    Student getByFirstName(String firstName);
}