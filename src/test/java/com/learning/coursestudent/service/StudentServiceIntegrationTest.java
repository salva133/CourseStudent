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
    private static final String TESTNAME = "Test";
    private static final String TESTNAME1 = "Test1";
    private static final String TESTNAME2 = "Test2";
    private static final String TESTDOB = "1993-12-10";

    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentService service;

    @Test
    void find_student_by_lastName_in_studentRepository() {
        //GIVEN
        service.createStudent(new StudentPojo(TESTNAME, TESTNAME, TESTDOB));
        //WHEN
        List<Student> students = repository.findByLastName(TESTNAME);
        Optional<Student> result = students.stream().filter(student -> student.getLastName().contains(TESTNAME)).findFirst();
        //THEN
        assert result.isPresent();
    }

    @Test
    void find_students_by_lastName_in_studentRepository1() {
        //GIVEN
        service.createStudentBatch(Set.of(new StudentPojo(TESTNAME1, TESTNAME1, TESTDOB),
                new StudentPojo(TESTNAME2, TESTNAME2, TESTDOB)));
        //WHEN
        List<Student> students = repository.findByLastName(TESTNAME1);
        Stream<Student> result = students.stream().filter(student -> student.getLastName().contains(TESTNAME1));
        //THEN
        assert result.anyMatch(student -> student.getLastName().contains(TESTNAME1));
    }

    @Test
    void find_students_by_lastName_in_studentRepository2() {
        //GIVEN
        service.createStudentBatch(Set.of(new StudentPojo(TESTNAME1, TESTNAME1, TESTDOB),
                new StudentPojo(TESTNAME2, TESTNAME2, TESTDOB)));
        //WHEN
        List<Student> students = repository.findByLastName(TESTNAME2);
        Stream<Student> result = students.stream().filter(student -> student.getLastName().contains(TESTNAME2));
        //THEN
        assert result.anyMatch(student -> student.getLastName().contains(TESTNAME2));
    }
}
