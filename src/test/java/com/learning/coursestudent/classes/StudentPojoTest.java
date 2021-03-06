package com.learning.coursestudent.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StudentPojoTest {

    @Test
    void testEquals() {
        // GIVEN
        StudentPojo pojo1 = new StudentPojo();
        pojo1.setFirstName("Liev");
        pojo1.setLastName("Schreiber");
        StudentPojo pojo2 = new StudentPojo();
        pojo2.setFirstName("Liev");
        pojo2.setLastName("Schreiber");
        // WHEN
        var resultActual = pojo1.equals(pojo2);
        //THEN
        var resultExpected = true;
        assertEquals(resultExpected, resultActual);
    }

    @Test
    void testNotEquals() {
        // GIVEN
        StudentPojo pojo1 = new StudentPojo("Liev","Schreiber");
        StudentPojo pojo2 = new StudentPojo("Liev","Schreiber");
        pojo2.setDateOfBirth("1980-01-01");
        // WHEN
        var resultActual = pojo1.equals(pojo2);
        //THEN
        var resultExpected = true;
        assertNotEquals(resultExpected, resultActual);
    }
}