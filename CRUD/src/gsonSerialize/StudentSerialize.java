package gsonSerialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Student;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class StudentSerialize implements JsonSerializer<Student> {
    @Override
    public JsonElement serialize(Student student, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        object.addProperty("id", student.getId());
        object.addProperty("name", student.getName());
        object.addProperty("date", formatter.format(student.getDate()));
        for (Gender e : Gender.values()) {
            if (student.getGender().equals(e.toString())) {
                object.addProperty("gender", e.getGender());
            }
        }
        if (student.getGroup() != null) {
            object.addProperty("group", student.getGroup().getNumber());
        } else {
            object.addProperty("group", "-");
        }
        object.addProperty("delete", student.getId());

        return object;
    }
}