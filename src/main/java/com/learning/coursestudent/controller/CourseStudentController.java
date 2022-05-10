package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import com.learning.coursestudent.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class CourseStudentController {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    //GETTER
    @GetMapping(value = "course")
    public ResponseEntity<List<Course>> getAllCourses() {
        return null; // Needs correct return from Service;
    }

    @GetMapping(value = "student")
    public ResponseEntity<List<Student>> getAllStudents() {
        return null; // Needs correct return from Service
    }
    //GETTER

    //POSTER
    @PostMapping(value = "course")
    public String newCourse(@RequestBody CoursePojo coursePojo) {
        return null; // Needs correct return from Service
    }

    @PostMapping(value = "course-batch")
    public String newCourseBatch(@RequestBody List<CoursePojo> coursePojoList) {
        return null; // Needs correct return from Service
    }

    @PostMapping(value = "student")
    public String newStudent(@RequestBody StudentPojo studentPojo) {
        return null; // Needs correct return from Service
    }

    @PostMapping(value = "student-batch")
    public String newStudentBatch(@RequestBody Set<StudentPojo> studentPojoList) {
        return new StudentService(studentRepository, courseRepository).createStudentBatch(studentPojoList); // Needs Überarbeitung because not elegant genug
    }
    //POSTER
}
