package workWithBase.serviceInterfaces;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TeacherServiceInterface {
    void insert(String name, LocalDate birthday, Gender gender);

    void delete(int id);

    void setName(int id, String newName);

    void setBirthday(int id, LocalDate birthday);

    void setGender(int id, String gender);

    List<Teacher> getTeachers(Map<String, Object> parameters);

    String getTeachersLength(String filter);

    List<Teacher> getTeachersForGroup(Map<String, Object> parameters);

    String getTeachersForGroupLength(int idGroup, String filter);

    List<Teacher> getNewTeachersForGroup(Map<String, Object> parameters);

    String getNewTeachersForGroupLength(int idGroup, String filter);

    void addGroup(int idTeacher, int numberGroup);

    void deleteGroup(int idTeacher, int numberGroup);
}