package com.learning.coursestudent.classes;

import java.util.Objects;

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

    @Override
    public String toString() {
        return pojo + ", Error = " + exception.getMessage() + " //";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FailedStudentWrapper wrapper = (FailedStudentWrapper) o;
        return pojo.equals(wrapper.pojo) && exception.equals(wrapper.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pojo, exception);
    }
}
