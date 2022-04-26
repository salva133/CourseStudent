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
            System.out.println("Course \"" + course.getCourseName() + "\" has been created");
        } catch (NameExpectedException e) {
            System.out.println("A name was expected");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Data integrity has been violated, rethrowing to ApiRequestException");
            throw new ApiRequestException("");
        }
        return "newCourse check";
    }


    @PostMapping(value = "student")
    public String newStudent(@RequestBody StudentPojo studentPojo) {

        try {
            Student student = new Student(studentPojo, ageLimit);
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
        return "newStudent check";
    }

/*   @PostMapping(value = "student-with-course")
    public String newStudentWithCourse(@RequestBody StudentPojo studentPojo, CoursePojo coursePojo) {
//Student
        LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
        try {
//Validation-IFs for Student
            if (student.getDateOfBirth().isAfter(LocalDate.now())) {
                throw new AgeException(student.dobIsInFutureValidation(student.getFullName(), student.getDateOfBirth()));
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

  */


    @PostMapping(value = "student-batch")
    public String newStudentBatch(@RequestBody List<StudentPojo> studentPojoList) {

        //for (int i = 0; i < studentPojoList.size(); i++) { // old FOR-Head

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
    /*
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
        } catch() {

        }
    }
    */

        }
        return "newStudentBatch check";
    }
}