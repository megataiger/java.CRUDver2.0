package workWithBase.interfaces;

import objectForStrokeBase.Student;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface StudentInterface {
    Student selectStudent(int idStudent) throws SQLException;

    List<Student> selectStudent() throws SQLException;

    List<Student> selectStudent(String nameStudent) throws SQLException;

    List<Student> selectStudent(LocalDate birthday) throws SQLException;

    List<Student> selectGroup(int idGroup) throws SQLException;

    void insert(Student student) throws SQLException;

    void update(Student student) throws SQLException;

    void delete(Student student) throws SQLException;
}
