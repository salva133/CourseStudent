package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.exceptions.CreationFailedException;
import com.learning.coursestudent.repos.CourseRepository;
import com.learning.coursestudent.repos.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;

@Controller
public class CourseStudentController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping(value = "create-everything")
    public String createEverything() throws CreationFailedException {
        Course course1 = new Course(1, "Math",null);
        Course course2 = new Course(2, "IT",null);
        Course course3 = new Course(3, "History",null);
        Student student1 = new Student(1, "Felix", "Springer",null);
        Student student2 = new Student(2, "Franz", "Josef",null);
        Student student3 = new Student(3, "Mark", "Ford",null);
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        if (course1 != null && course2 != null && course3 != null && student1 != null && student2 != null && student3 != null) {
            return "Courses and Students have been created.";
        } else {
            throw new CreationFailedException("Creation of Course \"" + course1.getCourseName() + "\" has failed");
        }
    }

    @GetMapping(value = "create-course-all")
    public String createCourseAll() throws CreationFailedException {
        String courseName1 = "Math";
        String courseName2 = "IT";
        String courseName3 = "History";
        Course course1   = new Course(1, courseName1,null);
        courseRepository.save(course1);
        Course course2   = new Course(2, courseName2,null);
        courseRepository.save(course1);
        Course course3   = new Course(3, courseName3,null);
        courseRepository.save(course1);

        if (course1 != null && course2 != null && course3 != null) {
            return "Courses \"" + course1.getCourseName() + "\", \"" + course2.getCourseName() + "\" and \"" + course3.getCourseName() + "\" have been created";
        } else {
            throw new CreationFailedException("Creation of Course \"" + course1.getCourseName() + "\" has failed");
        }
    }
    @GetMapping(value = "create-course-math")
    public String createCourse1() throws CreationFailedException {
        String courseName = "Math";
        Course course1 = new Course(1, courseName,null);
        courseRepository.save(course1);

        if (course1 != null) {
            return "Course \"" + course1.getCourseName() + "\" has been created";
        } else {
            throw new CreationFailedException("Creation of Course \"" + course1.getCourseName() + "\" has failed");
        }
    }
    @GetMapping(value = "create-course-it")
    public String createCourse2() throws CreationFailedException {
        String courseName = "IT";
        Course course2   = new Course(2, courseName,null);
        courseRepository.save(course2);

        if (course2 != null) {
            return "Course \"" + course2.getCourseName() + "\" has been created";
        } else {
            throw new CreationFailedException("Creation of Course \"" + course2.getCourseName() + "\" has failed");
        }
    }
    @GetMapping(value = "create-course-history")
    public String createCourse3() throws CreationFailedException {
        String courseName = "History";
        Course course3   = new Course(3, courseName,null);
        courseRepository.save(course3);
        if (course3 != null) {
            return "Course \"" + course3.getCourseName() + "\" has been created";
        } else {
            throw new CreationFailedException("Creation of Course \"" + course3.getCourseName() + "\" has failed");
        }
    }
    @GetMapping(value = "create-student-felixspringer")
    public String createStudent1() throws CreationFailedException {
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student1   = new Student(1, studentFirstName,studentLastName,null);
        studentRepository.save(student1);
        if (student1 != null) {
            return "Student \"" + student1.getLastName() + ", " + student1.getFirstName() + "\" has been created";
        } else {
            throw new CreationFailedException("Creation of Student \"" + student1.getLastName() + ", " + student1.getFirstName() + "\" has failed");
        }
    }
    @GetMapping(value = "create-student-franzjosef")
    public String createStudent2() throws CreationFailedException {
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student2   = new Student(2, studentFirstName,studentLastName,null);
        studentRepository.save(student2);
        if (student2 != null) {
            return "Student \"" + student2.getLastName() + ", " + student2.getFirstName() + "\" has been created";
        } else {
            throw new CreationFailedException("Creation of Student \"" + student2.getLastName() + ", " + student2.getFirstName() + "\" has failed");
        }
    }
    @GetMapping(value = "create-student-markford")
    public String createStudent3() throws CreationFailedException {
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student3   = new Student(3, studentFirstName,studentLastName,null);
        studentRepository.save(student3);
        if (student3 != null) {
            return "Student \"" + student3.getLastName() + ", " + student3.getFirstName() + "\" has been created";
        } else {
            throw new CreationFailedException("Creation of Student \"" + student3.getLastName() + ", " + student3.getFirstName() + "\" has failed");
        }
    }
    /*
    @GetMapping(value = "hello")
    public String hello() {
        return "Hello";
    }
    */
}