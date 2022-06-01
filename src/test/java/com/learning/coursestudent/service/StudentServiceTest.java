package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.StudentPojo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentServiceTest {

    private StudentPojo pojo;
    private Set<StudentPojo> pojoList = new HashSet<>();

    private static final String RESPONSE = "Success";

    @Mock
    private StudentService studentService;

    @Test
    void createStudent() throws Exception {
        pojo = new StudentPojo("Test", "Test");
        //GIVEN
        when(studentService.createStudent(pojo)).thenReturn(RESPONSE);
        //WHEN
        String result = studentService.createStudent(pojo);
        //THEN
        assert (result.equals(RESPONSE));
    }

    @Test
    void createStudentBatch() throws Exception {
        pojoList.add(new StudentPojo("Test1", "Test1"));
        pojoList.add(new StudentPojo("Test2", "Test2"));
        //GIVEN
        when(studentService.createStudentBatch(pojoList)).thenReturn(RESPONSE);
        //WHEN
        String result = studentService.createStudentBatch(pojoList);
        //THEN
        assert (result.equals(RESPONSE));
    }
}