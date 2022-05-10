package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.StudentPojo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentServiceTest {
    private final StudentService service;

    public StudentServiceTest(StudentService service) {
        this.service = service;
    }

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