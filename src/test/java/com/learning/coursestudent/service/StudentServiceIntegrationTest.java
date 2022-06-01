package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentServiceIntegrationTest {
    private static final String RESPONSE = "Success";

    @MockBean
    private StudentRepository repository;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService service;

    @Test
    void find_student_by_lastName_in_studentRepository() throws Exception {
        //GIVEN
        service.createStudent(new StudentPojo("Test", "Test"));
        when(repository.findByLastName("Test")).thenReturn(any());
        //WHEN
        String result = mvc.perform(get("/student-by-lastname")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        //THEN
        assert (result.equals(RESPONSE)); // Hier weitermachen
    }
}
