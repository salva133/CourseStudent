package com.learning.repos;

import com.learning.classes.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>{

    Course findByCourseId(long courseId);
    Course findByCourseName(String courseName);


}
