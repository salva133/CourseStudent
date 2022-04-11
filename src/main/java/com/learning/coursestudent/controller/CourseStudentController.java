package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CustomException;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repos.CourseRepository;
import com.learning.coursestudent.repos.StudentRepository;
import org.hibernate.PropertyValueException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
public class CourseStudentController {
    short ageLimit = 12;

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    /*@GetMapping(value = "course")
    public List<Course> getCourse() {
        List<Course> course = CourseRepository.findAll();
        return course;
    }*/

    @PostMapping(value = "new-course")
    public String newCourse(String courseName) {
        try {
            Course course = new Course(courseName);
            courseRepository.save(course);
            return "Course \"" + courseName + "\" has been created";
        } catch (PropertyValueException e) {
            e.printStackTrace();
            return "Course \"" + courseName + "\" could not be created.";
        }
    }

    @ResponseStatus
    @PostMapping(value = "new-student")
    public String newStudent(StudentPojo studentPojo) {
        LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);

        if (student.getDateOfBirth().isAfter(LocalDate.now())) {
            return new CustomException().DOBIsInFuture(student.getFullName(), student.getDateOfBirth());
        }
        if (student.getAge() < ageLimit) {
            return new CustomException().StudentTooYoungException(student.getFullName(), student.getId(), ageLimit);
        }

        try {
            studentRepository.save(student);
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\", born \"" + student.getDateOfBirth() + "\" and therefore " + student.getAge() + " years old, has been created";
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Use the following date format: YYYY-MM-DD",studentPojo.getDateOfBirth(),0);
        } catch (Exception e) {
            e.printStackTrace();
            return Student.class.getSimpleName()+" \"" + student.getFullName() + "\" could not be created.";
        }

    }
/*
    @PostMapping("new-student-batch")
    public String newStudentBatch(StudentPojo studentPojo) {
        LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);

        if (student.getDateOfBirth().isAfter(LocalDate.now())) {
            return new CustomException().DOBIsInFuture(student.getFullName(), student.getDateOfBirth());
        }
        if (student.getAge() < ageLimit) {
            return new CustomException().StudentTooYoungException(student.getFullName(), student.getId(), ageLimit);
        }

        try {
            studentRepository.save(student);
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\", born \"" + student.getDateOfBirth() + "\" and therefore " + student.getAge() + " years old, has been created";
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Use the following date format: YYYY-MM-DD",studentPojo.getDateOfBirth(),0);
        } catch (Exception e) {
            e.printStackTrace();
            return Student.class.getSimpleName()+" \"" + student.getFullName() + "\" could not be created.";
        }
    }

 */
/*    // Soll einen Student mit Course anlegen, nachdem newStudent und newCourse funktionieren werde ich hieran weiterarbeiten
    @PostMapping(value = "new-student-with-course")
    public String newStudentWithCourse(@RequestBody String firstName,String lastName,String courseName) {
        newStudent(firstName,lastName, newCourse(courseName));
    //    return Statics.newStudentWithCourseSuccess(firstName,lastName,courseName);
    }*/
}