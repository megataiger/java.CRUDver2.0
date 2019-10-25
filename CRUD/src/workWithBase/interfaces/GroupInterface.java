package workWithBase.interfaces;

import objectForStrokeBase.Group;
import java.sql.SQLException;

public interface GroupInterface {
    Group selectGroupById (int idGroup) throws SQLException;

    Group selectGroupByNumber(int numberGroup) throws SQLException;

    void select() throws SQLException;

    void insert(Group group) throws SQLException;

    void update(Group group) throws SQLException;

    void delete(Group group) throws SQLException;

    void selectStudent(Group group) throws SQLException;

    void selectTeacher(Group group) throws SQLException;

    void insertTeacher(Group group, int idTeacher) throws SQLException;

    void updateTeacher(Group group, int oldTeacherId, int newTeacherId)
            throws SQLException;

    void deleteTeacher(Group group, int idTeacher) throws SQLException;
}
