package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Gender;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CourseStudentRestControllerIntegrationTest {

    private static final String STUDENT_BATCH_REQUEST = """
            [
                {
                    "firstName": "Johnathan",
                    "lastName": "Archer",
                    "dateOfBirth": "1954-07-30",
                    "courseName": "Composition",
                    "mail": "j.archer@googlemail.de"
                },
                {
                    "firstName": "Arnold",
                    "lastName": "Schwarzenegger",
                    "dateOfBirth": "1947-07-30",
                    "courseName": "Composition",
                    "mail": "a.schwarzenegger@skynet.com"
                }
            ]""";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void newStudentBatch() throws Exception {
        //GIVEN
        mvc.perform(post("/student-batch")
                        .content(STUDENT_BATCH_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //WHEN
        List<Student> result = studentRepository.findByFirstName("Arnold");
        //THEN
        assert result.size() == 1;
        Student Arni = result.get(0);
        assert Arni.getFirstName().equals("Arnold");
        assert Arni.getLastName().equals("Schwarzenegger");
        assert Arni.getDateOfBirth().getYear() == 1954;
        assert Arni.getDateOfBirth().getMonthValue() == 7;
        assert Arni.getDateOfBirth().getDayOfMonth() == 30;
        assert Arni.getCourse().getName().equals("Composition");
        assert Arni.getMail().equals("a.schwarzenegger@skynet.com");
    }

    @Test
    void getStudent() throws Exception {
        //GIVEN
        Student student = new Student("Test");
        student.setFirstName("Test");
        student.setGender(Gender.MALE);
        student.setMail("test@test.com");
        studentRepository.save(student);
        //WHEN - THEN
        mvc.perform(get("/student")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Test"))
                .andExpect(jsonPath("$[0].lastName").value("Test"))
                .andExpect(jsonPath("$[0].mail").value("test@test.com"))
                .andReturn().getResponse().getContentAsString();
    }
}