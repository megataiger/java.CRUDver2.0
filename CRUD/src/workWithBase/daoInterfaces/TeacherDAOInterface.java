package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface TeacherDAOInterface {
    Teacher findById(int idTeacher) throws SQLException;

    List<Teacher> getAll() throws SQLException;

    List<Teacher> findByName(String nameTeacher) throws SQLException;

    List<Teacher> findByDate(LocalDate birthday) throws SQLException;

    void save(Teacher teacher) throws SQLException;

    void update(Teacher teacher) throws SQLException;

    void delete(Teacher teacher) throws SQLException;
}
