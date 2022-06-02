package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
class CourseServiceTest {
    private static final String RESPONSE = "Success";
    private static final String TESTNAME = "Test";

    @Mock
    private CourseService service;

    @Mock
    private CourseRepository repository;

    @Test
    void getAllCourses() {
        //GIVEN
        Course student = new Course(TESTNAME);
        repository.save(student);
        when(repository.findAll()).thenReturn(List.of());
        //WHEN
        List<Course> courses = repository.findAll();
        //THEN
        assert courses.equals(anyList());
    }

    @Test
    void createCourse() {
        CoursePojo coursePojo = new CoursePojo(TESTNAME);
        //GIVEN
        when(service.createCourse(coursePojo)).thenReturn(RESPONSE);
        //WHEN
        String result = service.createCourse(coursePojo);
        //THEN
        assert (result.equals(RESPONSE));
    }

    @Test
    void createCourseBatch() {
        Set<CoursePojo> coursePojoSet = new HashSet<>();
        coursePojoSet.add(new CoursePojo("Test1"));
        coursePojoSet.add(new CoursePojo("Test2"));
        //GIVEN
        when(service.createCourseBatch(coursePojoSet)).thenReturn(RESPONSE);
        //WHEN
        String result = service.createCourseBatch(coursePojoSet);
        //THEN
        assert (result.equals(RESPONSE));
    }
}