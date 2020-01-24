package gsonSerialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import objectForStrokeBase.Teacher;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;

public class TeacherEasySerialize implements JsonSerializer<Teacher> {
    @Override
    public JsonElement serialize(Teacher teacher, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        object.addProperty("id", teacher.getId());
        object.addProperty("name", teacher.getName());
        object.addProperty("birthday", formatter.format(teacher.getDate()));

        return object;
    }
}