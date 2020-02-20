package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;

import java.util.List;

public interface GroupDAOInterface {
    Group findById(int idGroup);

    Group selectGroupByNumber(int numberGroup);

    List getAll();

    void save(Group group);

    void update(Group group);

    void delete(Group group);

    List getGroups(String filter, int page, int length, String orderType);

    String getGroups(String filter);

    String getLengthTable();

    List getGroupForTeacher
            (int teacherId, int page, int length, String orderBy, String filter);

    String getGroupForTeacher
            (int teacherId, String filter);

    List getNewGroupForTeacher
            (int teacherId, int page, int length, String orderBy, String filter);

    String getNewGroupForTeacher
            (int teacherId, String filter);
}
