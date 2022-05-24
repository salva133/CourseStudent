package com.learning.coursestudent.classes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListSerializer extends StdSerializer<List<Student>> {
    public CustomListSerializer() {
        this(null);
    }

    public CustomListSerializer(Class<List<Student>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Student> students,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException {

        List<Integer> ids = new ArrayList<>();
        for (Student student : students) {
            ids.add((int) student.getId());
        }
        generator.writeObject(ids);
    }
}
