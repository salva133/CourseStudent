package com.learning.coursestudent.controller;

import com.learning.coursestudent.classes.*;
import com.learning.coursestudent.repos.CourseRepository;
import com.learning.coursestudent.repos.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class CourseStudentController {
    short ageLimit = 12;

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseStudentController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping(value = "course")
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "student")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @ResponseStatus
    @PostMapping(value = "new-course")
    public String newCourse(CoursePojo coursePojo) {
        Course course = new Course(coursePojo.getCourseName());
        courseRepository.save(course);
        return "Course \"" + coursePojo.getCourseName() + "\" has been created"+
                System.lineSeparator()+"HTTP-Status: "+HttpStatus.CREATED;
    }

    @ResponseStatus
    @PostMapping(value = "new-student")
    public String newStudent(StudentPojo studentPojo) throws DateTimeParseException {
        LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
        Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
//Exception-IFs
        if (student.getDateOfBirth().isAfter(LocalDate.now())) {
            return new CustomException().DOBIsInFutureException(student.getFullName(), student.getDateOfBirth());
        }
        if (student.getAge() < ageLimit) {
            return new CustomException().StudentTooYoungException(student.getFullName(), student.getId(), ageLimit);
        }

        try {
            studentRepository.save(student);
            return Student.class.getSimpleName() + " \"" + student.getFullName() + "\" - born \"" + student.getDateOfBirth()
                    + "\" and therefore " + student.getAge() + " years old - has been created"+
                    System.lineSeparator()+"HTTP-Status: "+HttpStatus.CREATED;
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Data parsed is invalid: "+e.getParsedString()+
                    System.lineSeparator()+"Use the following date format: YYYY-MM-DD",studentPojo.getDateOfBirth(),0);
        } catch (Exception e) {
            return Student.class.getSimpleName()+" \"" + student.getFullName() + "\" could not be created."+
                    System.lineSeparator()+"HTTP Status: "+HttpStatus.NOT_ACCEPTABLE;
        }
    }



    @PostMapping(value = "new-student-with-course")
    public String newStudentWithCourse(StudentPojo studentPojo,CoursePojo coursePojo) {
        try {
            LocalDate dateOfBirth = LocalDate.parse(studentPojo.getDateOfBirth());
            Student student = new Student(studentPojo.getFirstName(), studentPojo.getLastName(), dateOfBirth);
            Course course = new Course(coursePojo.getCourseName());
            student.setCourse(course);
            courseRepository.save(course);
            studentRepository.save(student);

            return "newStudentWithCourse complete";

        } catch (IllegalStateException e) {
            e.getCause();
        }
        return String.valueOf(HttpStatus.BAD_REQUEST);
    }
}

/*      //Planned feature
    @PostMapping(value = "new-student-batch")
    public String newStudentWithCourse(StudentPojo studentPojo) {
    }
 */
