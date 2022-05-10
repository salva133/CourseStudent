package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.StudentPojo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentServiceTest {

    private StudentService service;

    @Test
    void testCreateStudent() {
        StudentPojo pojo = new StudentPojo();
        pojo.setLastName("Hansen");
        service.createStudent(pojo);
        String lastName = String.valueOf(service.studentRepository.getByLastName("Hansen"));
        assertEquals("Hansen", lastName);
    }
}