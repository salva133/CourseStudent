package com.learning.coursestudent.repository;

import com.learning.coursestudent.classes.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findById(long id);

    Course findCourseByName(String name);

    List<Course> findCoursesByName(String name);
}