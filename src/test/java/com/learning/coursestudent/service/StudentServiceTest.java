package com.learning.coursestudent.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentServiceTest {


    private static final String RESPONSE = "Success";

    private static final String STUDENT_REQUEST =
            "{firstName\": \"Johnathan\",\n" +
            "\"lastName\": \"Archer\",\n" +
            "\"dateOfBirth\": \"1954-07-30\",\n" +
            "\"courseName\": \"Composition}";


    @MockBean
    private StudentService studentService;

    @Test
    void newStudentBatch() throws Exception {
        //GIVEN
        when(studentService.createStudentBatch(anySet())).thenReturn(RESPONSE);
        //WHEN
//        String result = mvc.perform(post("/student-batch")
//                        .content(STUDENT_REQUEST)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andReturn().getResponse().getContentAsString();
        //THEN
//        assert (result.equals(RESPONSE));
    }
}