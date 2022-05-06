package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.*;
import com.learning.coursestudent.exception.AgeException;
import com.learning.coursestudent.exception.ApiRequestException;
import com.learning.coursestudent.exception.DateFormatException;
import com.learning.coursestudent.exception.NameExpectedException;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class CourseStudentController {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    @Value("${ageLimit:12}")
    short ageLimit;
    @Value("${myDebug:false}")
    boolean myDebug;

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
    // Benötigt Test
    @PostMapping(value = "set-course")
    public String setCourseForStudent(@RequestBody StudentPojo studentPojo) {

        try {
            studentRepository.getById(studentPojo.getId()).setCourse(courseRepository.findByName(studentPojo.getCourseName()));
        } catch (NameExpectedException e) {
            System.out.println("A Name for the course was expected");
        }
        return "setCourseForStudent check";
    }

    @PostMapping(value = "course")
    public String newCourse(@RequestBody CoursePojo coursePojo) {
        try {
            Course course = new Course(coursePojo);
            courseRepository.save(course);
            System.out.println("## Course \"" + course.getName() + "\" has been created ##");
        } catch (NameExpectedException e) {
            System.out.println("A name was expected");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Data integrity has been violated, rethrowing to ApiRequestException");
            throw new ApiRequestException("");
        }
        return "newCourse check";
    }

    @PostMapping(value = "course-batch")
    public String newCourseBatch(@RequestBody List<CoursePojo> coursePojoList) {
        for (CoursePojo pojo : coursePojoList) {
            try {
                Course course = new Course(pojo);
                courseRepository.save(course);
                System.out.println("## Course \"" + course.getName() + "\" has been created ##");
            } catch (NameExpectedException e) {
                System.out.println("A name was expected");
            } catch (DataIntegrityViolationException e) {
                System.out.println("Data integrity has been violated, rethrowing to ApiRequestException");
                throw new ApiRequestException("Request has failed");
            }
        }
        return "Process of creating new courses has been completed";
    }

    @PostMapping(value = "student")
    public String newStudent(@RequestBody StudentPojo studentPojo) {

        try {
            Course course = courseRepository.findByName(studentPojo.getCourseName());
            Student student = new Student(studentPojo, ageLimit, course);
            studentRepository.save(student);
            System.out.println("## Student \"" + student.getFullName() + "\" created ##");
        } catch (java.lang.NullPointerException e) {
            System.out.println("Value of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
        } catch (DateTimeParseException e) {
            System.out.println("The date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" could not be parsed");
        } catch (AgeException e) {
            System.out.println("The Age of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
        } catch (DateFormatException e) {
            System.out.println("Date Format of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
        }
        return "newStudent check";
    }

    @PostMapping(value = "student-batch")
    public String newStudentBatch(@RequestBody Set<StudentPojo> studentPojoList) {
        Set<FailedStudentWrapper> creationFailedRecordList = new HashSet<>();
        for (StudentPojo pojo : studentPojoList) {
            try {
                Course course = courseRepository.findByName(pojo.getCourseName());
                Student student = new Student(pojo, ageLimit, course);
                studentRepository.save(student);
                if (course != null) {
                    System.out.println("## Student \"" + student.getFullName() + "\" has been created and assigned to course \"" + course.getName() + "\" ##");
                } else {
                    System.out.println("## Student \"" + student.getFullName() + "\" has been created ##");
                }
            } catch (AgeException e) {
                System.out.println("The Age is not valid");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (DataIntegrityViolationException e) {
                System.out.println("The integrity of the data has been violated");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (PropertyValueException e) {
                System.out.println("Value property is invalid");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (DateTimeParseException e) {
                System.out.println("The date could not be parsed");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (NullPointerException e) {
                System.out.println("Value is null");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (DateFormatException e) {
                System.out.println("Date Format is not valid");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            }
        }

        if (myDebug) {
            if (creationFailedRecordList.size() == 1) {
                System.out.println("Failed record: " + creationFailedRecordList);
                return "Process of creating new students has been completed" +
                        System.lineSeparator() + "There was one record out of " + studentPojoList.size() + " records which could not be created" +
                        System.lineSeparator() + "Failed record: " + creationFailedRecordList;
            }
            if (creationFailedRecordList.size() > 1) {
                System.out.println("Failed records: " + creationFailedRecordList);
                return "Process of creating new students has been completed" +
                        System.lineSeparator() + "There were " + creationFailedRecordList.size() + " out of " + studentPojoList.size() + " records which could not be created" +
                        System.lineSeparator() + "Failed records: " + creationFailedRecordList;
            }
        }
        if (creationFailedRecordList.size() == 1) {
            System.out.println("Failed record: " + creationFailedRecordList);
            return "Process of creating new students has been completed" +
                    System.lineSeparator() + "There was one record out of " + studentPojoList.size() + " records which could not be created";
        }
        if (creationFailedRecordList.size() > 1) {
            System.out.println("Failed records: " + creationFailedRecordList);
            return "Process of creating new students has been completed" +
                    System.lineSeparator() + "There were " + creationFailedRecordList.size() + " out of " + studentPojoList.size() + " records which could not be created";
        }
        return "Process of creating new students has been completed";
    }
}
