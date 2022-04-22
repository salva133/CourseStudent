package com.learning.coursestudent.service;

import com.learning.coursestudent.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;
}
