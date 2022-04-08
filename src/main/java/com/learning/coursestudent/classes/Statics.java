package com.learning.coursestudent.classes;

import com.google.gson.Gson;

public class Statics {
    public static String returnObjectAsJSON(Object object) { // in jeweilige klasse überführen und zu "toJson()" umbauen
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
