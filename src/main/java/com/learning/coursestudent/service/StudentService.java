package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.*;
import com.learning.coursestudent.exception.*;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
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

    public ResponseEntity<List<StudentResponse>> getStudentByMail(@RequestBody StudentPojo studentPojo) {
        return new ResponseEntity<>(studentRepository
                .findByMail(studentPojo.getMail())
                .stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    public String createStudent(@RequestBody StudentPojo studentPojo) {

        try {
            Set<Course> course = Collections.singleton(courseRepository.findCourseByName(studentPojo.getCourseName()));
            Student student = new Student(studentPojo, ageLimit, course);
            if (!studentRepository.findByMail(student.getMail()).isEmpty()) {
                throw new DuplicateObjectException("The requested mail address '" + student.getMail() + "' is already existing, " +
                        "either through the similarity of names " +
                        "or through manual input." +
                        "Please use the manual input to choose a mail address that is not already existing.");
            }
            studentRepository.save(student);
            logger.info("## Student \"" + student.getFullName() + "\" created ##");
            return "Process createStudent has been finished";
        } catch (NullDateException | DateTimeParseException | AgeException | DateFormatException |
                 InvalidMailValueException e) {
            logger.error(e.getMessage());
            return "The creation of the student failed." +
                    NEWLINE + "Please see the log or contact your System Administrator for further information.";
        }
    }

    public String createStudentBatch(@RequestBody Set<StudentPojo> studentPojoList) {
        Set<FailedStudentWrapper> creationFailedRecordList = new HashSet<>();
        for (StudentPojo studentPojo : studentPojoList) {
            try {
                Set<Course> courses = Collections.singleton(courseRepository.findCourseByName(studentPojo.getCourseName()));
                Student student = new Student(studentPojo, ageLimit, courses);
                if (!studentRepository.findByMail(student.getMail()).isEmpty()) {
                    throw new DuplicateObjectException("The requested mail address '" + student.getMail() + "' is already existing, " +
                                    "either through the similarity of names " +
                                    "or through manual input." +
                                    "Please use the manual input to choose a mail address that is not already existing.");
                }
                studentRepository.save(student);
                if (courses.stream().anyMatch(Objects::nonNull)) {
                    logger.info("## Student \"" + student.getFullName() + "\" has been created and assigned to course " + courses + " ##");
                } else if (courses.stream().anyMatch(Objects::isNull)) {
                    logger.info("## Student \"" + student.getFullName() + "\" has been created ##");
                }
            } catch (DateFormatException | NullPointerException | DateTimeParseException |
                     DataIntegrityViolationException | PropertyValueException | ConstraintViolationException |
                     DuplicateObjectException e) {
                logger.error(e.getMessage());
                creationFailedRecordList.add(new FailedStudentWrapper(studentPojo, e));
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