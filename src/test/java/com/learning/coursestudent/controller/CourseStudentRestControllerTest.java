package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
class CourseStudentRestControllerTest {

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
        List<Course> courseList = courseRepository.findAll();
        boolean exists = courseList.size() > 0;

        //then
        assertThat("At least one course exists",exists);
    }

    @Test
    void given_student_when_any_student_exists_then_true() {
        // given
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Test");
        studentRepository.save(student);

        //when
        List<Student> studentList = studentRepository.findAll();
        boolean exists = studentList.size() > 0;

        //then
        assertThat("At least one course exists",exists);
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