package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.Student;
import com.learning.coursestudent.classes.StudentPojo;
import com.learning.coursestudent.repos.CourseRepository;
import com.learning.coursestudent.repos.StudentRepository;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.learning.coursestudent.classes.Statics.returnObjectAsJSON;
import static java.lang.System.lineSeparator;

@RestController
public class CourseStudentController {
    Logger logger = LoggerFactory.getLogger(CourseStudentController.class);

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
            return "Course \"" + courseName + "\" has been created: " + lineSeparator() + returnObjectAsJSON(course);
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
        try {
            studentRepository.save(student);
            return "Student \"" + student.getFullName() + "\" has been created";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Student \"" + student.getFullName() + "\" could not be created.";
        }
    }

/*
    //Geplant für KW15
    @ResponseStatus
    @PostMapping(value = "new-student-batch")
    public String newStudentBatch(List<StudentPojo> studentPojoList) {
        LocalDate dateOfBirth = LocalDate.parse(studentPojoList.getDateOfBirth());
        Student student = new Student(studentPojoList.getFirstName(), studentPojoList.getLastName(), dateOfBirth);
        try {
            studentRepository.save(student);
            return "Student \"" + student.getFullName() + "\" has been created";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Student \"" + student.getFullName() + "\" could not be created.";
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