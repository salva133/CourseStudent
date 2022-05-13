package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.FailedStudentWrapper;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.exception.AgeException;
import com.learning.coursestudent.exception.DateFormatException;
import com.learning.coursestudent.exception.NullDateException;
import com.learning.coursestudent.repository.CourseRepository;
import com.learning.coursestudent.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    final StudentRepository studentRepository;
    final CourseRepository courseRepository;
    @Value("${ageLimit:12}")
    short ageLimit;
    @Value("${myDebug:false}")
    boolean myDebug;

    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    public String createStudent(@RequestBody StudentPojo studentPojo) {

        try {
            Course course = courseRepository.findByName(studentPojo.getCourseName());
            Student student = new Student(studentPojo, ageLimit, course);
            studentRepository.save(student);
            System.out.println("## Student \"" + student.getFullName() + "\" created ##");
        } catch (NullDateException e) {
            System.out.println("Date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is null");
            throw e;
        } catch (DateTimeParseException e) {
            System.out.println("The date of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" could not be parsed");
            throw e;
        } catch (AgeException e) {
            System.out.println("The Age of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
            throw e;
        } catch (DateFormatException e) {
            System.out.println("Date Format of \"" + studentPojo.getLastName() + ", " + studentPojo.getFirstName() + "\" is not valid");
            throw e;
        }
        return "Process of creating new student has been completed";
    }

    public String createStudentBatch(@RequestBody Set<StudentPojo> studentPojoList) {
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

    public void setAgeLimit(short ageLimit) {
        this.ageLimit = ageLimit;
    }
}