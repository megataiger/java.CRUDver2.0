package workWithBase.daoInterfaces;

import objectForStrokeBase.Teacher;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface TeacherDAOInterface {
    Teacher selectTeacher(int idTeacher) throws SQLException;

    List<Teacher> selectTeacher() throws SQLException;

    List<Teacher> selectTeacher(String nameTeacher) throws SQLException;

    List<Teacher> selectTeacher(LocalDate birthday) throws SQLException;

    void insert(Teacher teacher) throws SQLException;

    void update(Teacher teacher) throws SQLException;

    void delete(Teacher teacher) throws SQLException;

    void selectGroups(Teacher teacher) throws SQLException;

    void insertGroup(Teacher teacher, int groupId) throws SQLException;

    void updateGroup(Teacher teacher, int oldGroupId, int newGroupId)
            throws SQLException;

    void deleteGroup(Teacher teacher, int groupId) throws SQLException;
}
