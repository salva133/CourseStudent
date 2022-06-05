package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.*;
import com.learning.coursestudent.exception.AgeException;
import com.learning.coursestudent.exception.DateFormatException;
import com.learning.coursestudent.exception.InvalidMailValueException;
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
import java.util.HashSet;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;
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
            Course course = courseRepository.findCourseByName(studentPojo.getCourseName());
            Student student = new Student(studentPojo, ageLimit, course);
            studentRepository.save(student);
            logger.log(Logger.Level.INFO, "## Student \"" + student.getFullName() + "\" created ##");
        } catch (NullDateException e) {
            logger.error("Date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
            logger.error(e.getMessage());
            throw e;
        } catch (DateTimeParseException e) {
            logger.error("The date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" could not be parsed");
            logger.error(e.getMessage());
            throw e;
        } catch (AgeException e) {
            logger.error("The Age of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
            logger.error(e.getMessage());
            throw e;
        } catch (DateFormatException e) {
            logger.error("Date Format of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
            logger.error(e.getMessage());
            throw e;
        }
        return "Process createStudent has been finished";
    }

    public String createStudentBatch(@RequestBody Set<StudentPojo> studentPojoList) {
        Set<FailedStudentWrapper> creationFailedRecordList = new HashSet<>();
        for (StudentPojo pojo : studentPojoList) {
            try {
                Course course = courseRepository.findCourseByName(pojo.getCourseName());
                Student student = new Student(pojo, ageLimit, course);
                studentRepository.save(student);
                if (course != null) {
                    logger.log(Logger.Level.INFO, "## Student \"" + student.getFullName() + "\" has been created and assigned to course \"" + course.getName() + "\" ##");
                } else {
                    logger.log(Logger.Level.INFO, "## Student \"" + student.getFullName() + "\" has been created ##");
                }
            } catch (AgeException e) {
                logger.error("The Age is not valid");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (DataIntegrityViolationException e) {
                logger.error("The integrity of the data has been violated");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (PropertyValueException e) {
                logger.error("Value property is invalid");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (DateTimeParseException e) {
                logger.error("The date could not be parsed");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (NullPointerException e) {
                logger.error("Value is null");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (DateFormatException e) {
                logger.error("Date Format is not valid");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            }
        }

        if (myDebug) {
            if (creationFailedRecordList.size() == 1) {
                logger.warn("Failed record: " + creationFailedRecordList);
                return "Process of creating new students has been completed" +
                        NEWLINE + "Although there was one record out of " + studentPojoList.size() + " records which could not be created" +
                        NEWLINE + "Failed record: " + creationFailedRecordList;
            }
            if (creationFailedRecordList.size() > 1) {
                logger.warn("Failed records: " + creationFailedRecordList);
                return "Process of creating new students has been completed" +
                        NEWLINE + "Although there were " + creationFailedRecordList.size() + " out of " + studentPojoList.size() + " records which could not be created" +
                        NEWLINE + "Failed records: " + creationFailedRecordList;
            }
        }
        if (creationFailedRecordList.size() == 1) {
            logger.warn("Failed record: " + creationFailedRecordList);
            return "Process of creating new students has been completed" +
                    NEWLINE + "Although there was one record out of " + studentPojoList.size() + " records which could not be created";
        }
        if (creationFailedRecordList.size() > 1) {
            logger.warn("Failed records: " + creationFailedRecordList);
            return "Process of creating new students has been completed" +
                    NEWLINE + "Although there were " + creationFailedRecordList.size() + " out of " + studentPojoList.size() + " records which could not be created";
        }
        return "Process of creating new students has been completed";
    }

    public void setAgeLimit(short ageLimit) {
        this.ageLimit = ageLimit;
    }
}