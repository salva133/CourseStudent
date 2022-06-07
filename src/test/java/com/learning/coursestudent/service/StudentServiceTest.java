package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest {

    private static final String RESPONSE = "Success";
    private static final String TESTNAME = "Test";

    @Mock
    private StudentService service;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Test
    void getAllStudents() {
        //GIVEN
        Student student = new Student(TESTNAME);
        studentRepository.save(student);
        when(studentRepository.findAll()).thenReturn(List.of());
        //WHEN
        List<Student> students = studentRepository.findAll();
        //THEN
        assert students.equals(anyList());
    }

    @Test
    void getStudentByLastName() {
        //GIVEN
        Student student = new Student(TESTNAME);
        studentRepository.save(student);
        when(studentRepository.findByLastName(TESTNAME)).thenReturn(List.of());
        //WHEN
        List<Student> students = studentRepository.findByLastName(TESTNAME);
        //THEN
        assert students.equals(anyList());
    }

    @Test
    void getStudentByCourse() {
        //GIVEN
        Set<Course> course = new HashSet<>();
        course.add(new Course(TESTNAME));
        courseRepository.saveAll(course);
        Student student = new Student(TESTNAME);
        student.setCourse(course);
        studentRepository.save(student);

        when(studentRepository.findByCourseName(TESTNAME)).thenReturn(List.of());
        //WHEN
        List<Student> students = studentRepository.findByCourseName(TESTNAME);
        //THEN
        assert students.equals(anyList());
    }

    @Test
    void createStudent() {
        StudentPojo studentPojo = new StudentPojo(TESTNAME, TESTNAME);
        //GIVEN
        when(service.createStudent(studentPojo)).thenReturn(RESPONSE);
        //WHEN
        String result = service.createStudent(studentPojo);
        //THEN
        assert (result.equals(RESPONSE));
    }

    @Test
    void createStudentBatch() {
        Set<StudentPojo> studentPojoSet = new HashSet<>();
        studentPojoSet.add(new StudentPojo("Test1", "Test1"));
        studentPojoSet.add(new StudentPojo("Test2", "Test2"));
        //GIVEN
        when(service.createStudentBatch(studentPojoSet)).thenReturn(RESPONSE);
        //WHEN
        String result = service.createStudentBatch(studentPojoSet);
        //THEN
        assert (result.equals(RESPONSE));
    }
}