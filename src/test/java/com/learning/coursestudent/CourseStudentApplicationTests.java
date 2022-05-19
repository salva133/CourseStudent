package com.learning.coursestudent;

import com.learning.coursestudent.classes.StudentPojo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Set;

@SpringBootTest
class CourseStudentApplicationTests {

    @Test
    public void StudentList(Set<StudentPojo> pojo) {
        Assert.notNull(pojo, "pojo must contain at least one object");
        Assert.noNullElements(pojo, "pojo object must not contain any null value");
    }
}
