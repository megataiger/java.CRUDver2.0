package workWithBase.serviceInterfaces;

import objectForStrokeBase.Gender;

import java.time.LocalDate;
import java.util.Map;

public interface TeacherServiceInterface {
    void insert(String name, LocalDate birthday, Gender gender);

    void delete(int id);

    void setName(int id, String newName);

    void setBirthday(int id, LocalDate birthday);

    void setGender(int id, String gender);

    String getTeachers(Map<String, Object> parameters);

    String getGroups(Map<String, Object> parameters);

    String getNewGroups(Map<String, Object> parameters);

    void addGroup(int idTeacher, int numberGroup);

    void deleteGroup(int idTeacher, int numberGroup);
}