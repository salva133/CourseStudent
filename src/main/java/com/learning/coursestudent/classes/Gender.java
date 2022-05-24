package com.learning.coursestudent.classes;

public enum Gender {
    MALE, FEMALE;

    public String getGender() {
        switch (this) {
            case MALE:
                return "Male";
            case FEMALE:
                return "Female";
            default:
                return null;
        }
    }
}
