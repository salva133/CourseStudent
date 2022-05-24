package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

class CourseStudentRestControllerTest {

    @Mock
    StudentRepository studentRepository;
    @Mock
    CourseRepository courseRepository;

    @Test
    void givenStudent_whenAtLeastOneStudentHasCourse_thenTrue() {

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
        assertThat("Students with Course exist", exists);

    }
}