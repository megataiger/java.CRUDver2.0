package gsonSerialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import objectForStrokeBase.Student;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class StudentEasySerialize implements JsonSerializer<Student> {
    @Override
    public JsonElement serialize(Student student, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        object.addProperty("name", student.getName());
        object.addProperty("birthday", formatter.format(student.getDate()));

        return object;
    }
}