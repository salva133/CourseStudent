package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.exception.*;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class CourseStudentController {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    short ageLimit = 12;
    String nl = System.lineSeparator();

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
    public String newStudent(@RequestBody StudentPojo studentPojo) throws NotCreatedException {
        LocalDate dateOfBirth = null;
        Student student;
        try {
            dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
            if (dateOfBirth == null) {
                throw new DateIsNullException("Date is null");
            }
        } catch (NullPointerException e) {
            System.out.println("Caught NPE");
            System.out.println("Verify data because the Date of Birth is null.");
        } catch (DateTimeParseException e) {
            System.out.println("Caught DateTimeParseException");
            System.out.println("The date could not be parsed");
            throw new AgeException("The date could not be parsed");
        } catch (DateIsNullException e) {
            System.out.println("Caught DateIsNullException");
            System.out.println("Date is null");
        }
        student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
//Validation-IFs
        try {
            if (student.getDateOfBirth().isAfter(LocalDate.now())) {
                throw new AgeException(student.DOBIsInFutureValidation(student.getFullName(), student.getDateOfBirth()));
            }
            if (student.getAge() < ageLimit) {
                throw new AgeException(student.StudentTooYoungValidation(student.getFullName(), ageLimit));
            }
        } catch (AgeException e) {
            System.out.println("Caught AgeException");
            System.out.println("The Age is not valid");
            throw new AgeNotValidException("The Age is not valid: " + e);
        }
//Try-Catch
        try {
            if (student.getDateOfBirth().isBefore(LocalDate.now()) && student.getAge() > ageLimit) {
                studentRepository.save(student);
                return Student.class.getSimpleName() + " \"" + student.getFullName() + "\" - born \"" + student.getDateOfBirth() + "\" and therefore " + student.getAge() + " years old - has been created";
            } else {
                System.out.println("Caught AgeException");
                throw new AgeException("Age is not valid!");
            }
        } catch (AgeException e) {
            System.out.println("Caught AgeException");
            System.out.println("Student did not get created");
            throw new NotCreatedException("Student did not get created", e);
        }
    }

    @PostMapping(value = "student-with-course")
    public String newStudentWithCourse(@RequestBody StudentPojo studentPojo, CoursePojo coursePojo) {
//Student
        LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
        try {
//Validation-IFs for Student
            if (student.getDateOfBirth().isAfter(LocalDate.now())) {
                throw new AgeException(student.DOBIsInFutureValidation(student.getFullName(), student.getDateOfBirth()));
            }
            if (student.getAge() < ageLimit) {
                throw new AgeException(student.StudentTooYoungValidation(student.getFullName(), ageLimit));
            }
        } catch (AgeException e) {
            System.out.println("The Age is not valid!" + e);
        }
//Course
        Course course = new Course(coursePojo.getCourseName());
//Setting Course for Student
        try {
            if (course.getCourseName() != null) {
                student.setCourse(course);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            student.setCourse(null);
            return "Course could not be found and has been emptied.";
        }

//Try-Catch  
        try {
            courseRepository.save(course);
            studentRepository.save(student);
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\" - born \"" + student.getDateOfBirth() + "\" and therefore " + student.getAge() + " years old - has been created with " + Course.class.getSimpleName() + " \"" + course.getCourseName() + "\"" +
                    nl + "HTTP-Status: " + HttpStatus.CREATED;
        } catch (DateTimeParseException e) {
            return "Parsed Date or Time is invalid: " + e.getParsedString() +
                    nl + "Please verify the format of date/time values in your file." +
                    nl + HttpStatus.NOT_ACCEPTABLE;
        } catch (InternalError e) {
            return "The creation of the Student/Course objects has failed. Please verify the given data." + HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            return "Holy Shit! " +
                    nl + HttpStatus.I_AM_A_TEAPOT;
        }
    }


    @PostMapping(value = "students")
    public String newStudentBatch(@RequestBody StudentPojo studentPojo, @RequestParam(value = "studentId") int studentId) {
        return "Endpoint check";
    }
    //POSTER
}