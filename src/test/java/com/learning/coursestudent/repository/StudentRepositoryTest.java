package com.learning.coursestudent.repository;

import com.learning.coursestudent.classes.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class StudentRepositoryTest {

    @Mock
    StudentRepository studentRepository;

    @Test
    public void myTest() throws Exception {
        studentRepository.save(new Student());
    }
}