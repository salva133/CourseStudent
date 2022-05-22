package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.repository.StudentRepository;
import com.learning.coursestudent.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseStudentRestController.class)
class CourseStudentRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @BeforeEach
    public void setup() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        Student student = new Student("Test");

        Mockito.when(studentRepository.findByLastName(student.getLastName())).thenReturn(student);
    }

    @Test
    void newStudentBatch() {
        Student student = studentService.getAllStudents();
    }
}