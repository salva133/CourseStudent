package com.learning.coursestudent.classes;

public class CoursePojo {
    String courseName;
    String aStringForTesting;
    int anIntForTesting;
/*
    public CoursePojo(String courseName) {
        this.courseName = courseName;
    }
 */

    public CoursePojo(String courseName, String aStringForTesting, int anIntForTesting) {
        this.courseName = courseName;
        this.aStringForTesting = aStringForTesting;
        this.anIntForTesting = anIntForTesting;
    }


    public String getCourseName() {
        return courseName;
    }

    public String getaStringForTesting() {
        return aStringForTesting;
    }

    public int getAnIntForTesting() {
        return anIntForTesting;
    }

}
