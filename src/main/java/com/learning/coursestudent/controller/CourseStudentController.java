package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.repos.CourseRepository;
import com.learning.coursestudent.repos.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseStudentController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping(value = "create-everything")
    public String createEverything() {
        Course course1 = new Course(1, "Math",null);
        courseRepository.save(course1);
        Course course2 = new Course(2, "IT",null);
        courseRepository.save(course2);
        Course course3 = new Course(3, "History",null);
        courseRepository.save(course3);
        Student student1 = new Student(1, "Felix", "Springer",null);
        studentRepository.save(student1);
        Student student2 = new Student(2, "Franz", "Josef",null);
        studentRepository.save(student2);
        Student student3 = new Student(3, "Mark", "Ford",null);
        studentRepository.save(student3);
        return "Courses and Students have been created.";
    }

    @GetMapping(value = "create-course-all")
    public String createCourseAll() {
        String courseName1 = "Math";
        String courseName2 = "IT";
        String courseName3 = "History";
        Course course1   = new Course(1, courseName1,null);
        try { // Vorlage für alle try-catches (re-throw?)
            courseRepository.save(course1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Course course2   = new Course(2, courseName2,null);
        courseRepository.save(course2);
        Course course3   = new Course(3, courseName3,null);
        courseRepository.save(course3);
        return "Courses \"" + course1.getCourseName() + "\", \"" + course2.getCourseName() + "\" and \"" + course3.getCourseName() + "\" have been created";
    }

    @GetMapping(value = "create-course-math")
    public String createCourse1() {
        String courseName = "Math";
        Course course1 = new Course(1, courseName,null);
        courseRepository.save(course1);
        List<Student> studentList = new ArrayList<>();
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student1   = new Student(studentFirstName,studentLastName,course1);
        studentRepository.save(student1);
        studentFirstName = "Franz";
        studentLastName = "Josef";
        Student student2   = new Student(studentFirstName,studentLastName,course1);
        studentRepository.save(student2);
        studentList.add(student1);
        studentList.add(student2);

        return "Course \"" + course1.getCourseName() + "\" has been created";
    }

    @GetMapping(value = "create-course-it")
    public String createCourse2() {
        String courseName = "IT";
        Course course2   = new Course(2, courseName,null);
        courseRepository.save(course2);

        return "Course \"" + course2.getCourseName() + "\" has been created";
    }

    @GetMapping(value = "create-course-history")
    public String createCourse3() {
        String courseName = "History";
        Course course3   = new Course(3, courseName,null);
        courseRepository.save(course3);

        return "Course \"" + course3.getCourseName() + "\" has been created";
    }

    @GetMapping(value = "create-student-felixspringer")
    public String createStudent1() {
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Course course = new Course("Math",null);
        courseRepository.save(course);
        Student student1   = new Student(1, studentFirstName,studentLastName,course);
        studentRepository.save(student1);

        return "Student \"" + student1.getLastName() + ", " + student1.getFirstName() + "\" has been created";
    }

    @GetMapping(value = "create-student-franzjosef")
    public String createStudent2() {
        String studentFirstName = "Franz";
        String studentLastName = "Josef";
        Student student2   = new Student(2, studentFirstName,studentLastName,null);
        studentRepository.save(student2);

        return "Student \"" + student2.getLastName() + ", " + student2.getFirstName() + "\" has been created";
    }

    @GetMapping(value = "create-student-markford")
    public String createStudent3() {
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student3   = new Student(3, studentFirstName,studentLastName,null);
        studentRepository.save(student3);

        return "Student \"" + student3.getLastName() + ", " + student3.getFirstName() + "\" has been created";
    }

    @PostMapping(value = "course")
    public String postCourse() {

        return "Course created";
    }

    @GetMapping(value = "course")
    public Course getCourse(long id) {
        return null;
    }
}