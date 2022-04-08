package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.repos.CourseRepository;
import com.learning.coursestudent.repos.StudentRepository;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.learning.coursestudent.classes.Statics.returnObjectAsJSON;
import static java.lang.System.lineSeparator;

@RestController
public class CourseStudentController {
    Logger logger = LoggerFactory.getLogger(CourseStudentController.class);

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping(value = "course")
    public void getCourse() {
        logger.info("received GET request for Course");
    }

    @PostMapping(value = "newCourse")
    public String newCourse(String courseName) {
        try {
            Course course = new Course(courseName);
            courseRepository.save(course);
            return "Course \"" + courseName + "\" has been created: " + lineSeparator() + returnObjectAsJSON(course);
        } catch (PropertyValueException e) {
            e.printStackTrace();
            System.out.println("Course \"" + courseName + "\" could not be created.");
            return "Course \"" + courseName + "\" could not be created.";
        }
    }

    @PostMapping(value = "newStudent")
    public String newStudent(String firstName,String lastName) {
        String fullName = lastName + ", " + firstName;
        try {
            Student student = new Student(firstName,lastName,fullName);
            studentRepository.save(student);
            return "Student \"" + fullName + "\" has been created: " + lineSeparator() + returnObjectAsJSON(student);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Student \"" + fullName + "\" could not be created.";
        }
    }

/*
    Geplantes Feature
    @PostMapping(value = "link-student")
*/
/*
        Soll einen Student mit Course anlegen, nachdem newStudent und newCourse funktionieren werde ich hieran weiterarbeiten
    @PostMapping(value = "/newStudentWithCourse")
    public String newStudentWithCourse(@RequestBody String firstName,String lastName,String courseName) {
        newStudent(firstName,lastName, newCourse(courseName));
        return Statics.newStudentWithCourseSuccess(firstName,lastName,courseName);
    }*/
}