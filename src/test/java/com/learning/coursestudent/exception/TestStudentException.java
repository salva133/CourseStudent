package com.learning.coursestudent.exception;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TestStudentException {

    @Mock
    private CourseRepository courseRepository;

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
        when(courseRepository.findCourseByName(any())).thenReturn(Collections.singleton(new Course(coursePojo)));

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
        when(courseRepository.findCourseByName(any())).thenReturn(Collections.singleton(new Course(coursePojo)));

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
        when(courseRepository.findCourseByName(any())).thenReturn(Collections.singleton(new Course(coursePojo)));

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
        when(courseRepository.findCourseByName(any())).thenReturn(Collections.singleton(new Course(coursePojo)));

        assertThrows(TooYoungException.class, () -> service.createStudent(studentPojo));
    }
}
