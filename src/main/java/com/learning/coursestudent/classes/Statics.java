package com.learning.coursestudent.classes;

import com.google.gson.Gson;

public class Statics {
    public static String returnObjectAsJSON(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static String newStudentWithCourseSuccess(String firstName, String lastName, String courseName) {
        return "Student \""+lastName+", "+firstName+"\" has been created with Course "+courseName+".";
    }
}
