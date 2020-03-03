package workWithBase.serviceInterfaces;

import objectForStrokeBase.Group;

import java.util.List;
import java.util.Map;

public interface GroupServiceInterface {
    Group getByNumber(int number);

    void insert(int number);

    void delete(int id);

    void setNumber(int id, int number);

    void addTeacher(int number, int idTeacher);

    void deleteTeacher(int number, int idTeacher);

    List<Group> getGroups(Map<String, Object> parameters);

    String getGroupsLength(String filter);

    List<Group> getGroupsForTeacher(Map<String, Object> parameters);

    String getGroupsForTeacherLength(int idTeacher, String filter);

    List<Group> getNewGroupsForTeacher(Map<String, Object> parameters);

    String getNewGroupsForTeacherLength(int idTeacher, String filter);
}