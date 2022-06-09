package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Gender;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.repository.CourseRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CourseStudentRestControllerIntegrationTest {

    private static final String TESTNAME = "Test";
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

    private static final String COURSE_BATCH_REQUEST = """
            [
                {
                    "courseName": "Composition"
                },
                {
                    "courseName": "Acoustics"
                }
            ]""";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void given_studentBatch_posted_when_getting_student_by_firstName_then_get_values() throws Exception {
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
        assert Arni.getDateOfBirth().toString().equals("1947-07-30");
        assert Arni.getMailUserName().equals("a.schwarzenegger@skynet.com");
    }

    @Test
    void when_student_in_repo_then_find_by_values() throws Exception {
        //WHEN
        Student student = new Student(TESTNAME);
        student.setFirstName(TESTNAME);
        student.setGender(Gender.MALE);
        student.setMailUserName("test@test.com");
        studentRepository.save(student);
        //THEN
        mvc.perform(get("/student")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value(TESTNAME))
                .andExpect(jsonPath("$[0].lastName").value(TESTNAME))
                .andExpect(jsonPath("$[0].mail").value("test@test.com"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void given_courseBatch_posted_when_getting_course_by_name_then_get_values() throws Exception {
        //GIVEN
        mvc.perform(post("/course-batch")
                        .content(COURSE_BATCH_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //WHEN
        List<Course> result = courseRepository.findCoursesByName("Composition");
        //THEN
        assert result.size() == 1;
        Course course = result.get(0);
        assert course.getName().equals("Composition");
    }

    @Test
    void when_course_in_repo_then_find_by_name() throws Exception {
        //WHEN
        Course course = new Course(TESTNAME);
        courseRepository.save(course);
        //THEN
        mvc.perform(get("/course")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(TESTNAME))
                .andReturn().getResponse().getContentAsString();
    }
}