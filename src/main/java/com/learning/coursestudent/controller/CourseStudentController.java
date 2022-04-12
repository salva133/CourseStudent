package com.learning.coursestudent.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CustomException;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repos.CourseRepository;
import com.learning.coursestudent.repos.StudentRepository;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
public class CourseStudentController {
    short ageLimit = 12;

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping(value = "course")
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "student")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @ResponseStatus
    @PostMapping(value = "new-course")
    public String newCourse(String courseName) {
        Course course = new Course(courseName);
        courseRepository.save(course);
        return "Course \"" + courseName + "\" has been created";
    }

    @ResponseStatus
    @PostMapping(value = "new-student")
    public String newStudent(StudentPojo studentPojo) throws DateTimeParseException {
        LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
//Exception-IFs
        if (student.getDateOfBirth().isAfter(LocalDate.now())) {
            return new CustomException().DOBIsInFutureException(student.getFullName(), student.getDateOfBirth());
        }
        if (student.getAge() < ageLimit) {
            return new CustomException().StudentTooYoungException(student.getFullName(), student.getId(), ageLimit);
        }

        try {
            studentRepository.save(student);
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\" - born \"" + student.getDateOfBirth()
                    + "\" and therefore " + student.getAge() + " years old - has been created";
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Data parsed is invalid: "+e.getParsedString()+System.lineSeparator()+
                    "Use the following date format: YYYY-MM-DD",studentPojo.getDateOfBirth(),0);
        } catch (Exception e) {
            return Student.class.getSimpleName()+" \"" + student.getFullName() + "\" could not be created.";
        }
    }

    @PostMapping("new-student-batch")
    public String newStudentBatch(StudentPojo studentPojo) throws UnsupportedEncodingException {
        LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
        try {
//Exception-IFs
            if (student.getDateOfBirth().isAfter(LocalDate.now())) {
                return new CustomException().DOBIsInFutureException(student.getFullName(), student.getDateOfBirth());
            }
            if (student.getAge() < ageLimit) {
                return new CustomException().StudentTooYoungException(student.getFullName(), student.getId(), ageLimit);
            }
//putting in the POJO data stream
            Reader r = new InputStreamReader(studentPojo, StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder().create();
            JsonStreamParser p = new JsonStreamParser(r);
            while (p.hasNext()) {
                JsonElement je = p.next();
                if (je.isJsonObject()) {
                    var m = gson.fromJson(je, Map.class);
                    studentRepository.save(student);
                } else {
                    throw new IOException();
                }
            }
            return "End of File.";
        } catch (IOException e) {
            throw new UnsupportedEncodingException("Format/Encoding is not supported. Please check the format of your data.");
        }
    }

/*    // Soll einen Student mit Course anlegen, nachdem newStudent und newCourse funktionieren werde ich hieran weiterarbeiten
    @PostMapping(value = "new-student-with-course")
    public String newStudentWithCourse(@RequestBody String firstName,String lastName,String courseName) {
        newStudent(firstName,lastName, newCourse(courseName));
    //    return Statics.newStudentWithCourseSuccess(firstName,lastName,courseName);
    }*/
}