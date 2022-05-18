package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.*;
import com.learning.coursestudent.exception.AgeException;
import com.learning.coursestudent.exception.DateFormatException;
import com.learning.coursestudent.exception.NullDateException;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.NonUniqueResultException;
import org.hibernate.PropertyValueException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    final static Logger logger = Logger.getLogger(StudentService.class);
    private static final String nl = System.lineSeparator();
    final StudentRepository studentRepository;
    final CourseRepository courseRepository;
    @Value("${ageLimit:12}")
    short ageLimit;
    @Value("${myDebug:false}")
    boolean myDebug;

    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Student>> getStudentFromCourse(@RequestBody CoursePojo coursePojo) {
        List<Student> studentArrayListAll = new ArrayList<>(studentRepository.findAll());

        for (int i = 1; i < studentRepository.findAll().size(); i++) {
            try {
                Student student = new Student(studentArrayListAll.get(i));
                if (!student.getCourse().getName().equals(coursePojo.getCourseName())) {
                    studentArrayListAll.remove(student);
                }
            } catch (NullPointerException | NonUniqueResultException e) {
                logger.log(Logger.Level.ERROR, e.getMessage());
                return null;
            }
        }
        return new ResponseEntity<>(studentArrayListAll, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    public String createStudent(@RequestBody StudentPojo studentPojo) {

        try {
            Course course = courseRepository.findByName(studentPojo.getCourseName());
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
        return "Process of creating new student has been completed";
    }

    @ResponseStatus(HttpStatus.CREATED)
    public String createStudentBatch(@RequestBody Set<StudentPojo> studentPojoList) {
        Set<FailedStudentWrapper> creationFailedRecordList = new HashSet<>();
        for (StudentPojo pojo : studentPojoList) {
            try {
                Course course = courseRepository.findByName(pojo.getCourseName());
                Student student = new Student(pojo, ageLimit, course);
                studentRepository.save(student);
                if (course != null) {
                    logger.log(Logger.Level.INFO, "## Student \"" + student.getFullName() + "\" has been created and assigned to course \"" + course.getName() + "\" ##");
                } else {
                    logger.log(Logger.Level.INFO, "## Student \"" + student.getFullName() + "\" has been created ##");
                }
            } catch (AgeException e) {
                logger.log(Logger.Level.ERROR, "The Age is not valid");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (DataIntegrityViolationException e) {
                logger.log(Logger.Level.ERROR, "The integrity of the data has been violated");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (PropertyValueException e) {
                logger.log(Logger.Level.ERROR, "Value property is invalid");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (DateTimeParseException e) {
                logger.log(Logger.Level.ERROR, "The date could not be parsed");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (NullPointerException e) {
                logger.log(Logger.Level.ERROR, "Value is null");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            } catch (DateFormatException e) {
                logger.log(Logger.Level.ERROR, "Date Format is not valid");
                creationFailedRecordList.add(new FailedStudentWrapper(pojo, e));
            }
        }

        if (myDebug) {
            if (creationFailedRecordList.size() == 1) {
                logger.log(Logger.Level.WARN, "Failed record: " + creationFailedRecordList);
                return "Process of creating new students has been completed" +
                        nl + "Although there was one record out of " + studentPojoList.size() + " records which could not be created" +
                        nl + "Failed record: " + creationFailedRecordList;
            }
            if (creationFailedRecordList.size() > 1) {
                logger.log(Logger.Level.WARN, "Failed records: " + creationFailedRecordList);
                return "Process of creating new students has been completed" +
                        nl + "Although there were " + creationFailedRecordList.size() + " out of " + studentPojoList.size() + " records which could not be created" +
                        nl + "Failed records: " + creationFailedRecordList;
            }
        }
        if (creationFailedRecordList.size() == 1) {
            logger.log(Logger.Level.WARN, "Failed record: " + creationFailedRecordList);
            return "Process of creating new students has been completed" +
                    nl + "Although there was one record out of " + studentPojoList.size() + " records which could not be created";
        }
        if (creationFailedRecordList.size() > 1) {
            logger.log(Logger.Level.WARN, "Failed records: " + creationFailedRecordList);
            return "Process of creating new students has been completed" +
                    nl + "Although there were " + creationFailedRecordList.size() + " out of " + studentPojoList.size() + " records which could not be created";
        }
        return "Process of creating new students has been completed";
    }

    public void setAgeLimit(short ageLimit) {
        this.ageLimit = ageLimit;
    }
}