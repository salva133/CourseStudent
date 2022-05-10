package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.exception.ApiRequestException;
import com.learning.coursestudent.exception.NameExpectedException;
import com.learning.coursestudent.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }

    public String createCourse(@RequestBody CoursePojo coursePojo) {
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

    public String createCourseBatch(@RequestBody List<CoursePojo> coursePojoList) {
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
}