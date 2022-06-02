package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.*;
import com.learning.coursestudent.service.CourseService;
import com.learning.coursestudent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class CourseStudentRestController {

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public CourseStudentRestController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    //GETTER
    @GetMapping(value = "course")
    public ResponseEntity<List<Course>> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(value = "student")
    public ResponseEntity<List<Student>> getAllStudents() {
        return studentService.getAllStudents();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/student-by-lastname")
    public ResponseEntity<List<StudentResponse>> getStudentByLastName(StudentPojo studentLastName) {
        return studentService.getStudentByLastName(studentLastName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/student-by-course")
    public ResponseEntity<List<StudentResponse>> getStudentByCourse(CoursePojo courseName) {
        return studentService.getStudentByCourse(courseName);
    }
    //GETTER

    //POSTER
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/course")
    public String newCourse(@RequestBody CoursePojo coursePojo) {
        return courseService.createCourse(coursePojo);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/course-batch")
    public String newCourseBatch(@RequestBody List<CoursePojo> coursePojoList) {
        return courseService.createCourseBatch(coursePojoList);
    }

    @PostMapping(value = "student")
    public String newStudent(@RequestBody StudentPojo studentPojo) {
        return studentService.createStudent(studentPojo);
    }

    @PostMapping(value = "student-batch")
    public String newStudentBatch(@RequestBody Set<StudentPojo> studentPojoList) {
        return studentService.createStudentBatch(studentPojoList);
    }
    //POSTER
}
