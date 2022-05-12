package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService service;

    @Test
    void should_throw_npe_when_dob_is_null() {
        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName("Star");
        studentPojo.setLastName("Lord");
        studentPojo.setId(1);
        studentPojo.setCourseName("Algebra");

        CoursePojo coursePojo = new CoursePojo();
        coursePojo.setCourseName("Algebra");
        when(courseRepository.findByName(any())).thenReturn(new Course(coursePojo));

        assertThrows(NullPointerException.class, () -> service.createStudent(studentPojo));
    }
}