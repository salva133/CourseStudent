package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.exception.DateFormatException;
import com.learning.coursestudent.exception.DobInFutureException;
import com.learning.coursestudent.exception.NullDateException;
import com.learning.coursestudent.exception.TooYoungException;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
    void should_throw_nulldateexception_when_dob_is_null() {
        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName("Star");
        studentPojo.setLastName("Lord");
        studentPojo.setCourseName("Algebra");
        studentPojo.setDateOfBirth(null);

        CoursePojo coursePojo = new CoursePojo();
        coursePojo.setCourseName("Algebra");
        when(courseRepository.findByName(any())).thenReturn(new Course(coursePojo));

        assertThrows(NullDateException.class, () -> service.createStudent(studentPojo));
    }

    @Test
    void should_throw_dateformatexception_when_dob_format_is_wrong() {
        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName("Star");
        studentPojo.setLastName("Lord");
        studentPojo.setCourseName("Algebra");
        studentPojo.setDateOfBirth("String.valueOf(LocalDateTime.of(1996,35,53,0,0))");

        CoursePojo coursePojo = new CoursePojo();
        coursePojo.setCourseName("Algebra");
        when(courseRepository.findByName(any())).thenReturn(new Course(coursePojo));

        assertThrows(DateFormatException.class, () -> service.createStudent(studentPojo));
    }

    @Test
    void should_throw_dobinfutureexception_when_dob_is_in_future() {
        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName("Star");
        studentPojo.setLastName("Lord");
        studentPojo.setCourseName("Algebra");
        studentPojo.setDateOfBirth("2112-01-01");

        CoursePojo coursePojo = new CoursePojo();
        coursePojo.setCourseName("Algebra");
        when(courseRepository.findByName(any())).thenReturn(new Course(coursePojo));

        assertThrows(DobInFutureException.class, () -> service.createStudent(studentPojo));
    }

    @Test
    void should_throw_tooyoungexception_when_dob_is_below_agelimit() {
        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName("Star");
        studentPojo.setLastName("Lord");
        studentPojo.setCourseName("Algebra");
        studentPojo.setDateOfBirth("2022-05-12");
        service.setAgeLimit((short) 12);

        CoursePojo coursePojo = new CoursePojo();
        coursePojo.setCourseName("Algebra");
        when(courseRepository.findByName(any())).thenReturn(new Course(coursePojo));

        assertThrows(TooYoungException.class, () -> service.createStudent(studentPojo));
    }

    @Test
    void should_get_record_when_using_findbyId_method() {
        StudentPojo studentPojo = new StudentPojo();
        studentPojo.setFirstName("Star");
        studentPojo.setLastName("Lord");
        studentPojo.setCourseName("Algebra");
        studentPojo.setDateOfBirth("2022-05-12");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
    }
}