package com.learning.coursestudent.repos;

import com.learning.coursestudent.classes.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course getByCourseId(long courseId);
    Course getByCourseName(String courseName);
}