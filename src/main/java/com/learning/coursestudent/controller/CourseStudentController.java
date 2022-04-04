package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.repos.CourseRepository;
import com.learning.coursestudent.repos.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseStudentController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private Course course;
    private Student student;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }
/*

    @PostMapping(value = "create-everything")
    public String createEverything() {
        Course   course = new Course("Math");
        courseRepository.save(course);
                 course = new Course("IT");
        courseRepository.save(course);
                 course = new Course("History");
        courseRepository.save(course);
        Student student = new Student("Felix", "Springer");
        studentRepository.save(student);
                student = new Student("Franz", "Josef");
        studentRepository.save(student);
                student = new Student("Mark", "Ford");
        studentRepository.save(student);
        return "Courses and Students have been created.";
    }
*/
    @PostMapping(value = "create-course-math")
    public String createCourse1() {
        String courseName = "Math";
        Course course = new Course(courseName);
        courseRepository.save(course);
        List<Student> studentList = new ArrayList<>();
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student1   = new Student(studentFirstName,studentLastName,course);
        studentRepository.save(student1);
        studentFirstName = "Franz";
        studentLastName = "Josef";
        Student student2   = new Student(studentFirstName,studentLastName,course);
        studentRepository.save(student2);
        studentList.add(student1);
        studentList.add(student2);
        return "Course \"" + course.getCourseName() + "\" has been created";
    }
/*
    @PostMapping(value = "create-course-it")
    public String createCourse2() {
        String courseName = "IT";
        Course course   = new Course(courseName);
        courseRepository.save(course);

        return "Course \"" + course.getCourseName() + "\" has been created";
    }

    @PostMapping(value = "create-course-history")
    public String createCourse3() {
        String courseName = "History";
        Course course   = new Course(courseName);
        courseRepository.save(course);

        return "Course \"" + course.getCourseName() + "\" has been created";
    }

    @PostMapping(value = "create-student-felixspringer")
    public String createStudent1() {
        String courseName = "Math";
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Course course = new Course(courseName);
        courseRepository.save(course);
        Student student   = new Student(studentFirstName,studentLastName,course);
        studentRepository.save(student);

        return "Student \"" + student.getLastName() + ", " + student.getFirstName() + "\" has been created";
    }

    @PostMapping(value = "create-student-franzjosef")
    public String createStudent2() {
        String studentFirstName = "Franz";
        String studentLastName = "Josef";
        Student student   = new Student(studentFirstName,studentLastName);
        studentRepository.save(student);

        return "Student \"" + student.getLastName() + ", " + student.getFirstName() + "\" has been created";
    }

    @PostMapping(value = "create-student-markford")
    public String createStudent3() {
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student   = new Student(studentFirstName,studentLastName);
        studentRepository.save(student);

        return "Student \"" + student.getLastName() + ", " + student.getFirstName() + "\" has been created";
    }
*/

    @PostMapping(value = "course")
    public String addCourse(String courseName) {
        try {
            Course course = new Course("Test");
            courseRepository.save(course);
            return "Courses \"" + course.getCourseName() + "\" has been created";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Course" + courseName + " could not be created.";
        }
    }

    @GetMapping(value = "course/{id}")
    public ResponseEntity<String> findByCourseId(@RequestParam ("courseName") String courseName) {
        try {
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            //return "Course" + courseRepository.findByCourseId(id).getCourseName() + " could not be found.";
            return new ResponseEntity<>("Course could not be found.", HttpStatus.NOT_FOUND);
        }
    }
}