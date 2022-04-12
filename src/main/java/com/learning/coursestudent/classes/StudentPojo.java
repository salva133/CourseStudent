package com.learning.coursestudent.classes;

import java.io.IOException;
import java.io.InputStream;

public class StudentPojo extends InputStream {
    String firstName;
    String lastName;
    String dateOfBirth;

    public StudentPojo(String firstName, String lastName, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
