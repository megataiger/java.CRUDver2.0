package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface GroapDAOInterface {
    Group selectGroupById (int idGroup) throws SQLException;

    Group selectGroupByNumber(int numberGroup) throws SQLException;

    List<Group> select() throws SQLException;

    void insert(Group group) throws SQLException;

    void update(Group group) throws SQLException;

    void delete(Group group) throws SQLException;

    List<Teacher> selectTeacher(Group group) throws SQLException;

    void insertTeacher(Group group, int idTeacher) throws SQLException;

    void updateTeacher(Group group, int oldTeacherId, int newTeacherId)
            throws SQLException;

    void deleteTeacher(Group group, int idTeacher) throws SQLException;
}
