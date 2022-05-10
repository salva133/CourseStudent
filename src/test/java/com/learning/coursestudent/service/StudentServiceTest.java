package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.StudentPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentServiceTest {

    private StudentService service;

    @BeforeEach
    void setUpStudentServiceTest() {
        StudentPojo pojo = new StudentPojo();
        pojo.setLastName("Hansen");
        service.createStudent(pojo);
    }

    @Test
    void testCreateStudent() {
        String lastName = String.valueOf(service.studentRepository.getByLastName("Hansen"));
        assertEquals("Hansen", lastName);
    }
}