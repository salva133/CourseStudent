package com.learning.coursestudent.classes;

import java.util.Objects;

public class CourseResponse {
    long id;
    String name;

    public CourseResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CourseResponse(Course course) {
        this(course.getId(), course.getName());
    }

    @Override
    public String toString() {
        return "Name = \"" + name + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseResponse that = (CourseResponse) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


}
