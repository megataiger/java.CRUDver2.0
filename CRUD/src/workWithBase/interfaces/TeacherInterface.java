package workWithBase.interfaces;

import objectForStrokeBase.Teacher;

import java.sql.SQLException;
import java.time.LocalDate;

public interface TeacherInterface {
    Teacher selectTeacher(int idTeacher) throws SQLException;

    void selectTeacher() throws SQLException;

    void selectTeacher(String nameTeacher) throws SQLException;

    void selectTeacher(LocalDate birthday) throws SQLException;

    void insert(Teacher teacher) throws SQLException;

    void update(Teacher teacher) throws SQLException;

    void delete(Teacher teacher) throws SQLException;

    void selectGroups(Teacher teacher) throws SQLException;

    void insertGroup(Teacher teacher, int groupId) throws SQLException;

    void updateGroup(Teacher teacher, int oldGroupId, int newGroupId)
            throws SQLException;

    void deleteGroup(Teacher teacher, int groupId) throws SQLException;
}
