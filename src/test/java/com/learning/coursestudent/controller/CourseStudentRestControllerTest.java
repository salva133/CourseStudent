package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CourseStudentRestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    StudentService studentService;

    @Test
    public void givenStudent_whenGetStudents_thenReturnJsonArray() throws Exception {

        Student student = new Student("Test");

        List<Student> allStudents = List.of(student);

        given(studentService.getAllStudents()).willReturn((ResponseEntity<List<Student>>) allStudents);

        mvc.perform(get("student"))
                .andExpect(status().isOk());
    }

}
