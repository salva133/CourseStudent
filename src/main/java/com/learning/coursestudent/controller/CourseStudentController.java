package com.learning.coursestudent.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.learning.coursestudent.classes.*;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

@RestController
public class CourseStudentController {
    short ageLimit = 12;

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

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
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Given Data is invalid")
    @ExceptionHandler(PropertyValueException.class)
    @PostMapping(value = "new-course")
    public String newCourse(CoursePojo coursePojo) {
        Course course = new Course(coursePojo.getCourseName());

        try {
            courseRepository.save(course);
            return "Course \"" + coursePojo.getCourseName() + "\" has been created" +
                    System.lineSeparator() + "HTTP-Status: " + HttpStatus.CREATED;
        } catch (PropertyValueException e) {
            return "I am sorry, but the name of the course you provided is empty or invalid in some other way. Please re-check the data you tried to insert." +
                    System.lineSeparator() + "Error message: " + e.getMessage() +
                    System.lineSeparator() + "HTTP Status: " + HttpStatus.FORBIDDEN;
        }
    }

    @ResponseStatus
    @PostMapping(value = "new-student")
    public String newStudent(StudentPojo studentPojo) {
        LocalDate dateOfBirth;
        Student student;
        try {
            dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        } catch (DateTimeException e) {
            return "Verify your data because the Date of Birth you provided is not valid."+
                    System.lineSeparator()+"Error message: "+e.getMessage();
        } catch (NullPointerException e) {
            return "Exception class: "+e.getClass().getSimpleName()+
                    System.lineSeparator()+"Exception Message: "+e.getMessage();
        }
        student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
//Exception-IFs
        if (student.getDateOfBirth().isAfter(LocalDate.now())) {
            return new CustomException().DOBIsInFutureException(student.getFullName(), student.getDateOfBirth());
        }
        if (student.getAge() < ageLimit) {
            return new CustomException().StudentTooYoungException(student.getFullName(), student.getId(), ageLimit);
        }
//Try-Catch
        try {
            studentRepository.save(student);
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\" - born \"" + student.getDateOfBirth()
                    + "\" and therefore " + student.getAge() + " years old - has been created"+
                    System.lineSeparator()+"HTTP-Status: "+HttpStatus.CREATED;
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Data parsed is invalid: "+e.getParsedString()+
                    System.lineSeparator()+"Use the following date format: YYYY-MM-DD",studentPojo.getDateOfBirth(),0);
        } catch (Exception e) {
            return Student.class.getSimpleName()+" \"" + student.getFullName() + "\" could not be created."+
                    System.lineSeparator()+"HTTP Status: "+HttpStatus.NOT_ACCEPTABLE;
        }
    }

    @PostMapping(value = "new-student-with-course")
    public String newStudentWithCourse(StudentPojo studentPojo,CoursePojo coursePojo) {
//Student
        LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
//Exception-IFs for Student
        if (student.getDateOfBirth().isAfter(LocalDate.now())) {
            return new CustomException().DOBIsInFutureException(student.getFullName(), student.getDateOfBirth());
        }
        if (student.getAge() < ageLimit) {
            return new CustomException().StudentTooYoungException(student.getFullName(), student.getId(), ageLimit);
        }
//Course
        Course course = new Course(coursePojo.getCourseName());
//Setting Course for Student
        try {
            student.setCourse(course);
        } catch (HttpClientErrorException.NotFound e) {
            student.setCourse(null);
            return "Course could not be found and has been set to NULL. Anyway, the course itself has been created."+
                    System.lineSeparator()+e.getLocalizedMessage()+
                    System.lineSeparator()+HttpStatus.NOT_FOUND;
        }

//Try-Catch  
        try {
            courseRepository.save(course);
            studentRepository.save(student);
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\" - born \"" + student.getDateOfBirth() + "\" and therefore " + student.getAge() + " years old - has been created with " + Course.class.getSimpleName() + " \"" + course.getCourseName() + "\"" +
                    System.lineSeparator() + "HTTP-Status: " + HttpStatus.CREATED;
        } catch (DateTimeParseException e) {
            return "Parsed Date or Time is invalid: " + e.getParsedString() +
                    System.lineSeparator() + "Please verify the format of date/time values in your file." +
                    System.lineSeparator() + HttpStatus.NOT_ACCEPTABLE;
        } catch (InternalError e) {
            return "The creation of the Student/Course objects has failed. Please verify the given data." + HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            return "Holy Shit! " +
                    System.lineSeparator() + HttpStatus.I_AM_A_TEAPOT;
        }
    }

        //Planned feature
    @PostMapping(value = "new-student-batch")
    public String newStudentBatch(JsonArray selections) {
        return null;
    }
    //POSTER
}