package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@SpringBootTest
public class StudentServiceIntegrationTest {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentService service;

    @Test
    void find_student_by_lastName_in_studentRepository() throws Exception {
        //GIVEN
        service.createStudent(new StudentPojo("Test", "Test", "1993-12-10"));
        //WHEN
        List<Student> students = repository.findByLastName("Test");
        Optional<Student> result = students.stream().filter(student -> student.getLastName().contains("Test")).findFirst();
        //THEN
        assert result.isPresent();
    }

    @Test
    void find_students_by_lastName_in_studentRepository() throws Exception {
        //GIVEN
        service.createStudentBatch(Set.of(new StudentPojo("Test1", "Test1", "1993-12-10"),
                new StudentPojo("Test2", "Test2", "1993-12-10")));
        //WHEN
        List<Student> students = repository.findByLastName("Test1");
        Stream<Student> result = students.stream().filter(student -> student.getLastName().contains("Test1"));
        //THEN
        assert result.anyMatch(student -> student.getLastName().contains("Test1"));
    }
}
