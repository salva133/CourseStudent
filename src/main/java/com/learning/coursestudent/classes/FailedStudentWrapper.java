package com.learning.coursestudent.classes;

public class FailedStudentWrapper extends Throwable {
    private final StudentPojo pojo;
    private final Exception exception;

    public FailedStudentWrapper(StudentPojo pojo, Exception exception) {
        String errorMessage = exception.getMessage();
        String pojoName = pojo.getLastName() + ", " + pojo.getFirstName();

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
