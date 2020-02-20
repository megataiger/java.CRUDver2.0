package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentDAOInterface {
    Student findById(int idStudent);

    List getAll();

    String getTableLength();

    List findByName(String nameStudent);

    List findByDate(LocalDate birthday);

    List findByGroup(Group group);

    void save(Student student);

    void update(Student student);

    void delete(Student student);

    List findByFilter(String filter, int page, int length, String orderBy);

    String findByFilter(String filter);

    List findByGroup
            (int groupId, int page, int length, String orderBy, String filter);

    String findByGroup(int groupId, String filter);

    String findByGroup(int groupId);
}
