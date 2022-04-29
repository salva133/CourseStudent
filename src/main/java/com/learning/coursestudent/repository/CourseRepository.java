package com.learning.coursestudent.repository;

import com.learning.coursestudent.classes.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findById(long id);

    Course findByName(String name);
}