package workWithBase.serviceInterfaces;

import java.util.Map;

public interface GroupServiceInterface {
    void insert(int number);

    void delete(int id);

    void setNumber(int id, int number);

    void addTeacher(int number, int idTeacher);

    void deleteTeacher(int number, int idTeacher);

    String getGroups(Map<String, Object> parameters);

    String getStudents(Map<String, Object> parameters);

    String getTeachers(Map<String, Object> parameters);

    String getNewTeachers(Map<String, Object> parameters);
}