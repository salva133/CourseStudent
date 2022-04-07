package com.learning.coursestudent.repos;

import com.learning.coursestudent.classes.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("select u from course")
    List<Course> getAllCourses();
    Course getByCourseId(long courseId);
    Course getByCourseName(String courseName);
}