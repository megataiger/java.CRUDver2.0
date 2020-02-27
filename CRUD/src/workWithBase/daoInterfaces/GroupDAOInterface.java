package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;

import java.util.List;
import java.util.Map;

public interface GroupDAOInterface {
    Group findById(int idGroup);

    Group selectGroupByNumber(int numberGroup);

    List getAll();

    void save(Group group);

    void update(Group group);

    void delete(Group group);

    List getGroups(Map<String, Object> parameters);

    String getGroups(String filter);

    List getGroupForTeacher
            (Map<String, Object> parameters);

    String getGroupForTeacher
            (int teacherId, String filter);

    List getNewGroupForTeacher
            (Map<String, Object> parameters);

    String getNewGroupForTeacher
            (int teacherId, String filter);

    List searchGroup(int number);
}
