package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.exception.ApiRequestException;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class CourseStudentController {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    short ageLimit = 12;
    String newline = System.lineSeparator();

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
        Course course = new Course(coursePojo.getCourseName());

        try {
            courseRepository.save(course);
            return "Course \"" + course.getCourseName() + "\" has been created";
        } catch (DataIntegrityViolationException e) {
            //Logging

            //Try to self repair

            //inform client
            throw new ApiRequestException("");
        }
    }

    @PostMapping(value = "student")
    public String newStudent(@RequestBody StudentPojo studentPojo) {
        LocalDate dateOfBirth;
        Student student;
        try {
            dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        } catch (DateTimeException e) {
            return "Verify your data because the Date of Birth you provided is not valid." +
                    newline + "Error message: " + e.getMessage();
        } catch (NullPointerException e) {
            return "Exception class: " + e.getClass().getSimpleName() +
                    newline + "Exception Message: " + e.getMessage();
        }
        student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
//Validation-IFs
        if (student.getDateOfBirth().isAfter(LocalDate.now())) {
            return student.DOBIsInFutureValidation(student.getFullName(), student.getDateOfBirth());
        }
        if (student.getAge() < ageLimit) {
            return student.StudentTooYoungValidation(student.getFullName(), ageLimit);
        }
//Try-Catch
        try {
            studentRepository.save(student);
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\" - born \"" + student.getDateOfBirth()
                    + "\" and therefore " + student.getAge() + " years old - has been created" +
                    newline + "HTTP-Status: " + HttpStatus.CREATED;
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Data parsed is invalid: " + e.getParsedString() +
                    newline + "Use the following date format: YYYY-MM-DD", studentPojo.getDateOfBirth(), 0);
        } catch (Exception e) {
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\" could not be created." +
                    newline + "HTTP Status: " + HttpStatus.NOT_ACCEPTABLE;
        }
    }

    @PostMapping(value = "student-with-course")
    public String newStudentWithCourse(@RequestBody StudentPojo studentPojo, CoursePojo coursePojo) {
//Student
        LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
//Validation-IFs for Student
        if (student.getDateOfBirth().isAfter(LocalDate.now())) {
            return student.DOBIsInFutureValidation(student.getFullName(), student.getDateOfBirth());
        }
        if (student.getAge() < ageLimit) {
            return student.StudentTooYoungValidation(student.getFullName(), ageLimit);
        }
//Course
        Course course = new Course(coursePojo.getCourseName());
//Setting Course for Student
        try {
            student.setCourse(course);
        } catch (HttpClientErrorException.NotFound e) {
            student.setCourse(null);
            return "Course could not be found and has been set to NULL. Anyway, the course itself has been created." +
                    newline + e.getLocalizedMessage() +
                    newline + HttpStatus.NOT_FOUND;
        }

//Try-Catch  
        try {
            courseRepository.save(course);
            studentRepository.save(student);
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\" - born \"" + student.getDateOfBirth() + "\" and therefore " + student.getAge() + " years old - has been created with " + Course.class.getSimpleName() + " \"" + course.getCourseName() + "\"" +
                    newline + "HTTP-Status: " + HttpStatus.CREATED;
        } catch (DateTimeParseException e) {
            return "Parsed Date or Time is invalid: " + e.getParsedString() +
                    newline + "Please verify the format of date/time values in your file." +
                    newline + HttpStatus.NOT_ACCEPTABLE;
        } catch (InternalError e) {
            return "The creation of the Student/Course objects has failed. Please verify the given data." + HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            return "Holy Shit! " +
                    newline + HttpStatus.I_AM_A_TEAPOT;
        }
    }


    @PostMapping(value = "students")
    public String newStudentBatch(@RequestBody StudentPojo studentPojo, @RequestParam(value = "studentId") int studentId) {
        return "Endpoint check";
    }
    //POSTER
}