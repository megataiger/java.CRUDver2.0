package workWithBase.daoInterfaces;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StudentDAOInterface {
    Student findById(int idStudent);

    List getAll();

    List findByName(String nameStudent);

    List findByDate(LocalDate birthday);

    List findByGroup(Group group);

    void save(Student student);

    void update(Student student);

    void delete(Student student);

    List findByFilter(Map<String, Object> parameters);

    String findByFilter(String filter);

    List findByGroup(Map<String, Object> parameters);

    String findByGroup(int groupId, String filter);

    String findByGroup(int groupId);
}
