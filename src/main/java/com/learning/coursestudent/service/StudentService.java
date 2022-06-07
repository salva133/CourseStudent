package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.*;
import com.learning.coursestudent.exception.AgeException;
import com.learning.coursestudent.exception.DateFormatException;
import com.learning.coursestudent.exception.NullDateException;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    final static Logger logger = Logger.getLogger(StudentService.class);
    private static final String NEWLINE = System.lineSeparator();
    final StudentRepository studentRepository;
    final CourseRepository courseRepository;
    @Value("${ageLimit:12}")
    short ageLimit;
    @Value("${myDebug:false}")
    boolean myDebug;

    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<StudentResponse>> getStudentByLastName(@RequestBody StudentPojo studentPojo) {
        return new ResponseEntity<>(studentRepository
                .findByLastName(studentPojo.getLastName())
                .stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<List<StudentResponse>> getStudentByCourse(@RequestBody CoursePojo coursePojo) {
        return new ResponseEntity<>(studentRepository
                .findByCourseName(coursePojo.getCourseName())
                .stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    public String createStudent(@RequestBody StudentPojo studentPojo) {

        try {
            Set<Course> course = Collections.singleton(courseRepository.findCourseByName(studentPojo.getCourseName()));
            Student student = new Student(studentPojo, ageLimit, course);
            studentRepository.save(student);
            logger.log(Logger.Level.INFO, "## Student \"" + student.getFullName() + "\" created ##");
        } catch (NullDateException e) {
            logger.log(Logger.Level.ERROR, "Date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
            logger.log(Logger.Level.ERROR, e.getMessage());
            throw e;
        } catch (DateTimeParseException e) {
            logger.log(Logger.Level.ERROR, "The date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" could not be parsed");
            logger.log(Logger.Level.ERROR, e.getMessage());
            throw e;
        } catch (AgeException e) {
            logger.log(Logger.Level.ERROR, "The Age of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
            logger.log(Logger.Level.ERROR, e.getMessage());
            throw e;
        } catch (DateFormatException e) {
            logger.log(Logger.Level.ERROR, "Date Format of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
            logger.log(Logger.Level.ERROR, e.getMessage());
            throw e;
        }
        return "Process createStudent has been finished";
    }

    public String createStudentBatch(@RequestBody Set<StudentPojo> studentPojoList) {
        Set<FailedStudentWrapper> creationFailedRecordList = new HashSet<>();
        for (StudentPojo studentPojo : studentPojoList) {
            try {
                Set<Course> courses = Collections.singleton(courseRepository.findCourseByName(studentPojo.getCourseName()));
                Student student = new Student(studentPojo, ageLimit, courses);
                studentRepository.save(student);
                if (courses.stream().anyMatch(Objects::nonNull)) {
                    logger.log(Logger.Level.INFO, "## Student \"" + student.getFullName() + "\" has been created and assigned to course " + courses + " ##");
                } else if (courses.stream().anyMatch(Objects::isNull)) {
                    logger.log(Logger.Level.INFO, "## Student \"" + student.getFullName() + "\" has been created ##");
                }
            } catch (AgeException e) {
                logger.log(Logger.Level.ERROR, "The Age is not valid");
                creationFailedRecordList.add(new FailedStudentWrapper(studentPojo, e));
            } catch (DataIntegrityViolationException e) {
                logger.log(Logger.Level.ERROR, "The integrity of the data has been violated");
                creationFailedRecordList.add(new FailedStudentWrapper(studentPojo, e));
            } catch (PropertyValueException e) {
                logger.log(Logger.Level.ERROR, "Value property is invalid");
                creationFailedRecordList.add(new FailedStudentWrapper(studentPojo, e));
            } catch (DateTimeParseException e) {
                logger.log(Logger.Level.ERROR, "The date could not be parsed");
                creationFailedRecordList.add(new FailedStudentWrapper(studentPojo, e));
            } catch (NullPointerException e) {
                logger.log(Logger.Level.ERROR, "Value is null");
                creationFailedRecordList.add(new FailedStudentWrapper(studentPojo, e));
            } catch (DateFormatException e) {
                logger.log(Logger.Level.ERROR, "Date Format is not valid");
                creationFailedRecordList.add(new FailedStudentWrapper(studentPojo, e));
            }
        }

        if (myDebug) {
            if (creationFailedRecordList.size() == 1) {
                logger.log(Logger.Level.WARN, "Failed record: " + creationFailedRecordList);
                return "Process of creating new students has been completed" +
                        NEWLINE + "Although there was one record out of " + studentPojoList.size() + " records which could not be created" +
                        NEWLINE + "Failed record: " + creationFailedRecordList;
            }
            if (creationFailedRecordList.size() > 1) {
                logger.log(Logger.Level.WARN, "Failed records: " + creationFailedRecordList);
                return "Process of creating new students has been completed" +
                        NEWLINE + "Although there were " + creationFailedRecordList.size() + " out of " + studentPojoList.size() + " records which could not be created" +
                        NEWLINE + "Failed records: " + creationFailedRecordList;
            }
        }
        if (creationFailedRecordList.size() == 1) {
            logger.log(Logger.Level.WARN, "Failed record: " + creationFailedRecordList);
            return "Process of creating new students has been completed" +
                    NEWLINE + "Although there was one record out of " + studentPojoList.size() + " records which could not be created";
        }
        if (creationFailedRecordList.size() > 1) {
            logger.log(Logger.Level.WARN, "Failed records: " + creationFailedRecordList);
            return "Process of creating new students has been completed" +
                    NEWLINE + "Although there were " + creationFailedRecordList.size() + " out of " + studentPojoList.size() + " records which could not be created";
        }
        return "Process of creating new students has been completed";
    }

    public void setAgeLimit(short ageLimit) {
        this.ageLimit = ageLimit;
    }
}