package com.learning.coursestudent.repository;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@DataJpaTest
class RepositoriesTest {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;

    @Test
    void given_course_when_any_course_exists_then_true() {
        // given
        Course course = new Course();
        course.setName("Test");
        courseRepository.save(course);

        //when
        boolean exists = courseRepository.count() > 0;

        //then
        assertTrue("At least one course exists", exists);
    }

    @Test
    void given_student_when_any_student_exists_then_true() {
        // given
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Test");
        studentRepository.save(student);

        //when
        boolean exists = studentRepository.count() > 0;

        //then
        assertThat("At least one course exists", exists);
    }

    @Test
    void given_student_when_at_least_one_student_has_course_then_true() {

        // given
        Course course = new Course();
        course.setName("Test");
        courseRepository.save(course);
        Student student = new Student();
        student.setCourse(course);
        student.setFirstName("Bob");
        student.setLastName("the Tester");
        studentRepository.save(student);

        // when
        List<Student> studentsByCourse = studentRepository.findByCourseName(course.getName());
        boolean exists = studentsByCourse.size() > 0;

        // then
        assertThat("Students with Course exists", exists);

    }
}