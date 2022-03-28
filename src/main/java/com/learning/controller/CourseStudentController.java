package com.learning.controller;

import com.learning.classes.Course;
import com.learning.classes.Student;
import com.learning.repos.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseStudentController {

    private final CourseRepository courseRepository;

    public CourseStudentController(CourseRepository courseRepository) { //Best practice
        this.courseRepository = courseRepository;
    }

    @GetMapping(value = "do-something")
    public String doSomething() {
        Course course   = new Course(1, "Math");
        Student student = new Student(1, "Franz","Josef");
        courseRepository.save();
        return "Course and Student have been created.";
    }
}


/*
Zuerst ein Objekt vom Typ Course und dann ein Student Objekt erzeugen. Dann prüfen, ob die erzeugt werden. Exception einbauen.
Wenn sie nicht gespeichert werden, analysieren.
 */