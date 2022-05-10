package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.service.CourseService;
import com.learning.coursestudent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class CourseStudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public CourseStudentController(StudentService studentService, CourseService courseService) {
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
    //GETTER

    //POSTER
    @PostMapping(value = "course")
    public String newCourse(@RequestBody CoursePojo coursePojo) {
        return courseService.createCourse(coursePojo);
    }

    @PostMapping(value = "course-batch")
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
