package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@SpringBootTest
public class CourseServiceIntegrationTest {
    private static final String TESTNAME = "Test";
    private static final String TESTNAME1 = "Test1";
    private static final String TESTNAME2 = "Test2";

    @Autowired
    private CourseRepository repository;

    @Autowired
    private CourseService service;

    @Test
    void find_student_by_lastName_in_studentRepository() {
        //GIVEN
        service.createCourse(new CoursePojo(TESTNAME));
        //WHEN
        List<Course> courses = repository.findCoursesByName(TESTNAME);
        Optional<Course> result = courses.stream()
                .filter(course -> course.getName()
                        .contains(TESTNAME))
                .findFirst();
        //THEN
        assert result.isPresent();
    }

    @Test
    void find_students_by_lastName_in_studentRepository1() {
        //GIVEN
        service.createCourseBatch(Set.of(new CoursePojo(TESTNAME1),
                new CoursePojo(TESTNAME2)));
        //WHEN
        List<Course> courses = repository.findCoursesByName(TESTNAME1);
        Stream<Course> result = courses.stream()
                .filter(course -> course.getName()
                        .contains(TESTNAME1));
        //THEN
        assert result.anyMatch(course -> course.getName().contains(TESTNAME1));
    }

    @Test
    void find_students_by_lastName_in_studentRepository2() {
        //GIVEN
        service.createCourseBatch(Set.of(new CoursePojo(TESTNAME1),
                new CoursePojo(TESTNAME2)));
        //WHEN
        List<Course> courses = repository.findCoursesByName(TESTNAME2);
        Stream<Course> result = courses.stream().filter(student -> student.getName().contains(TESTNAME2));
        //THEN
        assert result.anyMatch(student -> student.getName().contains(TESTNAME2));
    }
}