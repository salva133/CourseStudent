package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.exception.*;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class CourseStudentController {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    @Value("${ageLimit}")
    short ageLimit;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    //GETTER
    @GetMapping(value = "course")
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "student")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }
    //GETTER

    //POSTER
    @PostMapping(value = "course")
    public String newCourse(@RequestBody CoursePojo coursePojo) {
        try {
            Course course = new Course(coursePojo);
            courseRepository.save(course);
            System.out.println("## Course \"" + course.getCourseName() + "\" has been created ##");
        } catch (NameExpectedException e) {
            System.out.println("A name was expected");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Data integrity has been violated, rethrowing to ApiRequestException");
            throw new ApiRequestException("");
        }
        return "newCourse check";
    }

    @PostMapping(value = "course-obj")
    public Course newCourseObj(@RequestBody CoursePojo coursePojo) {
        try {
            Course course = new Course(coursePojo);
            courseRepository.save(course);
            System.out.println("## Course \"" + course.getCourseName() + "\" has been created ##");
            return course;
        } catch (NameExpectedException e) {
            System.out.println("A name was expected");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Data integrity has been violated, rethrowing to ApiRequestException");
            throw new ApiRequestException("");
        }
        return null;
    }


    @PostMapping(value = "student")
    public String newStudent(@RequestBody StudentPojo studentPojo) {

        try {
            Student student = new Student(studentPojo, ageLimit);
            studentRepository.save(student);
            System.out.println("## Student \"" + student.getFullName() + "\" created ##");
        } catch (NullPointerException e) {
            System.out.println("Data of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
        } catch (DateTimeParseException e) {
            System.out.println("The date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" could not be parsed");
        } catch (DateIsNullException e) {
            System.out.println("Date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
        } catch (AgeException e) {
            System.out.println("The Age of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
        } catch (DateFormatException e) {
            System.out.println("Date Format of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
        }
        return "newStudent check";
    }

    @PostMapping(value = "student-obj")
    public Student newStudentObj(@RequestBody StudentPojo studentPojo) {

        try {
            Student student = new Student(studentPojo, ageLimit);
            studentRepository.save(student);
            System.out.println("## Student \"" + student.getFullName() + "\" created ##");
            return student;
        } catch (NullPointerException e) {
            System.out.println("Data of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
        } catch (DateTimeParseException e) {
            System.out.println("The date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" could not be parsed");
        } catch (DateIsNullException e) {
            System.out.println("Date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
        } catch (AgeException e) {
            System.out.println("The Age of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
        } catch (DateFormatException e) {
            System.out.println("Date Format of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
        }
        return null;
    }

    @PostMapping(value = "student-with-course")
    public String newStudentWithCourse(@RequestBody StudentPojo studentPojo, @RequestBody CoursePojo coursePojo) {

        try {
            Student student = new Student(studentPojo, ageLimit);
            studentRepository.save(student);
            System.out.println("## student saved ##");

            Course course = new Course(coursePojo);
            courseRepository.save(course);
            System.out.println("## course saved ##");

            student.setCourse(course);
            System.out.println("## course set ##");
        } catch (NullPointerException e) {
            System.out.println("Data of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
        } catch (DateTimeParseException e) {
            System.out.println("The date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" could not be parsed");
        } catch (DateIsNullException e) {
            System.out.println("Date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
        } catch (AgeException e) {
            System.out.println("The Age of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
        } catch (DateFormatException e) {
            System.out.println("Date Format of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
        } catch (NameExpectedException e) {
            System.out.println("Name of \"" + coursePojo.getCourseName() + "\" is not valid");
        } catch (HttpMessageNotReadableException e) {
            System.out.println("Http Message is not readable");
        }
        return "newStudentWithCourse check";
    }


    @PostMapping(value = "student-batch")
    public String newStudentBatch(@RequestBody List<StudentPojo> studentPojoList) {

        for (StudentPojo pojo : studentPojoList) {
            try {
                Student student = new Student(pojo, ageLimit);
                studentRepository.save(student);
                System.out.println("##record created##");
            } catch (NullPointerException e) {
                System.out.println("Data is null");
            } catch (DateTimeParseException e) {
                System.out.println("The date could not be parsed");
            } catch (DateIsNullException e) {
                System.out.println("Date is null");
            } catch (AgeException e) {
                System.out.println("The Age is not valid");
            } catch (DateFormatException e) {
                System.out.println("Date Format is not valid");
            }
        }
        return "newStudentBatch check";
    }
}