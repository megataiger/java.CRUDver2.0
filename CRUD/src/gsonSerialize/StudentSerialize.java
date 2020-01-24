package gsonSerialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
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
        object.addProperty("birthday", formatter.format(student.getDate()));
        if (student.getGender().equals("MAN")) {
            object.addProperty("gender", "М");
        } else if (student.getGender().equals("WOMAN")) {
            object.addProperty("gender", "Ж");
        } else {
            object.addProperty("gender", "-");
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