package com.learning.coursestudent.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StudentPojoTest {

    @Test
    void testEquals() {
        // GIVEN
        StudentPojo pojo1 = new StudentPojo();
        pojo1.setId(1);
        pojo1.setFirstName("Liev");
        pojo1.setLastName("Schreiber");
        StudentPojo pojo2 = new StudentPojo();
        pojo2.setId(1);
        pojo2.setFirstName("Liev");
        pojo2.setLastName("Schreiber");
        pojo2.setDateOfBirth("1980-01-01");

        // WHEN
        var resultActual = pojo1.equals(pojo2);

        //THEN
        var resultExpected = true;
        assertNotEquals(resultExpected, resultActual);

    }
}