package workWithBase.daoInterfaces;

import objectForStrokeBase.Teacher;

import java.time.LocalDate;
import java.util.List;

public interface TeacherDAOInterface {
    Teacher findById(int idTeacher);

    List getAll();

    void save(Teacher teacher);

    void update(Teacher teacher);

    void delete(Teacher teacher);

    List findByName(String nameTeacher);

    List findByDate(LocalDate birthday);

    List selectTeachers(int page, int length, String filter, String orderBy);

    String selectTeachers(String filter);

    String getTableLength();

    List getTeachersForGroup(int groupId, int page, int length, String orderBy, String filter);

    String getTeachersForGroup(int groupId, String filter);

    List getNewTeachersForGroup(int groupId, int page, int length, String orderBy, String filter);

    String getNewTeachersForGroup(int groupId, String filter);
}
