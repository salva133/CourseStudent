package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.repos.CourseRepository;
import com.learning.coursestudent.repos.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseStudentController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }
/*
    @GetMapping(value = "do-something")
    public String doSomething() {
        Course course1   = new Course(1, "Math");
        Course course2   = new Course(2, "IT");
        Course course3   = new Course(3, "History");
        Student student1 = new Student(1, "Felix","Springer");
        Student student2 = new Student(2, "Franz","Josef");
        Student student3 = new Student(3, "Mark","Ford");
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        return "Courses and Students have been created.";
    }
*/
    @GetMapping(value = "create-course-math")
    public String createCourse1() {
        String courseName = "Math";
        Course course1   = new Course(1, courseName);
        courseRepository.save(course1);
        return "Course \"" +courseName+ "\" has been created";
    }
    @GetMapping(value = "create-course-it")
    public String createCourse2() {
        String courseName = "IT";
        Course course2   = new Course(1, courseName);
        courseRepository.save(course2);
        return "Course \"" +courseName+ "\" has been created";
    }
    @GetMapping(value = "create-course-history")
    public String createCourse3() {
        String courseName = "History";
        Course course3   = new Course(1, courseName);
        courseRepository.save(course3);
        return "Course \"" +courseName+ "\" has been created";
    }
    @GetMapping(value = "create-student-felixspringer")
    public String createStudent1() {
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student1   = new Student(1, studentFirstName,studentLastName);
        studentRepository.save(student1);
        return "Student \"" +studentLastName+ ", " +studentFirstName+"\" has been created";
    }
    @GetMapping(value = "create-student-franzjosef")
    public String createStudent2() {
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student2   = new Student(2, studentFirstName,studentLastName);
        studentRepository.save(student2);
        return "Student \"" +studentLastName+ ", " +studentFirstName+"\" has been created";
    }
    @GetMapping(value = "create-student-markford")
    public String createStudent3() {
        String studentFirstName = "Felix";
        String studentLastName = "Springer";
        Student student3   = new Student(3, studentFirstName,studentLastName);
        studentRepository.save(student3);
        return "Student \"" +studentLastName+ ", " +studentFirstName+"\" has been created";
    }
    /*
    @GetMapping(value = "hello")
    public String hello() {
        return "Hello";
    }
    */
}


/*
1. Zuerst ein Objekt vom Typ Course und dann ein Student Objekt erzeugen. Dann prüfen, ob die erzeugt werden.
2. Exception einbauen.
3. Wenn sie nicht gespeichert werden, analysieren.
4. Verknüpfung zu beiden Tabellen erzeugen, vorerst auskommentieren.
 */