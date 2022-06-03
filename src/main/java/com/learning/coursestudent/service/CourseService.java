package com.learning.coursestudent.service;

import com.learning.coursestudent.classes.Course;
import com.learning.coursestudent.classes.CoursePojo;
import com.learning.coursestudent.classes.CourseResponse;
import com.learning.coursestudent.exception.NameExpectedException;
import com.learning.coursestudent.repository.CourseRepository;
import com.sun.istack.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CourseService {

    final static Logger logger = Logger.getLogger(CourseService.class);
    final CourseRepository courseRepository;

    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<CourseResponse>> getCourseByName(@RequestBody CoursePojo coursePojo) {
        return new ResponseEntity<>(courseRepository
                .findCoursesByName(coursePojo.getCourseName())
                .stream()
                .map(CourseResponse::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    public String createCourse(@RequestBody CoursePojo coursePojo) {
        try {
            Course course = new Course(coursePojo);
            courseRepository.save(course);
            logger.severe("## Course \"" + course.getName() + "\" has been created ##");
        } catch (NameExpectedException e) {
            logger.severe("A name was expected");
        } catch (DataIntegrityViolationException e) {
            logger.severe("Data integrity has been violated, rethrowing to ApiRequestException");
        }
        return "Process createCourse has been finished";
    }

    public String createCourseBatch(@RequestBody Set<CoursePojo> coursePojoList) {
        for (CoursePojo pojo : coursePojoList) {
            try {
                Course course = new Course(pojo);
                courseRepository.save(course);
                logger.info("## Course \"" + course.getName() + "\" has been created ##");
            } catch (NameExpectedException e) {
                logger.severe(e.getMessage());
            } catch (PropertyValueException e) {
                logger.severe("Value of Property 'Name' is illegal");
            } catch (NullPointerException e) {
                logger.severe("Value is null");
            }
        }
        return "Process createCourseBatch has been finished";
    }
}