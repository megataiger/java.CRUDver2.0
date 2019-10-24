package workWithBase.Interfaces;

import objectForStrokeBase.Student;

import java.sql.SQLException;
import java.time.LocalDate;

public interface StudentInterface {
    Student selectStudent(int idStudent) throws SQLException;

    void selectStudent() throws SQLException;

    void selectStudent(String nameStudent) throws SQLException;

    void selectStudent(LocalDate birthday) throws SQLException;

    void selectGroup(int idGroup) throws SQLException;

    void insert(Student student) throws SQLException;

    void update(Student student) throws SQLException;

    void delete(Student student) throws SQLException;
}
