package com.learning.coursestudent.classes;

public class FailedStudentWrapper {
    private final StudentPojo pojo;
    private final Exception exception;

    public FailedStudentWrapper(StudentPojo pojo, Exception exception) {
        this.pojo = pojo;
        this.exception = exception;
    }

    public StudentPojo getPojo() {
        return pojo;
    }

    public Exception getException() {
        return exception;
    }
}
