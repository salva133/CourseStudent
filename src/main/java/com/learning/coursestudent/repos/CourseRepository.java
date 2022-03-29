package com.learning.coursestudent.repos;

import com.learning.coursestudent.classes.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Course findByCourseId(long courseId);
    Course findByCourseName(String courseName);
}
