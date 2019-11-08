package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface StudentDAOInterface {
    Student findById(int idStudent);

    List<Student> getAll();

    List<Student> findByName(String nameStudent);

    List<Student> findByDate(LocalDate birthday);

    List<Student> findByGroup(Group group);

    void save(Student student);

    void update(Student student);

    void delete(Student student);
}
