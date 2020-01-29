package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentDAOInterface {
    Student findById(int idStudent);

    List getAll();

    List findByName(String nameStudent);

    List findByDate(LocalDate birthday);

    List findByGroup(Group group);

    void save(Student student);

    void update(Student student);

    void delete(Student student);
}
