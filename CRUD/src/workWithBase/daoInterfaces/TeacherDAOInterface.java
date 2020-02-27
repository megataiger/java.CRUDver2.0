package workWithBase.daoInterfaces;

import objectForStrokeBase.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TeacherDAOInterface {
    Teacher findById(int idTeacher);

    List getAll();

    void save(Teacher teacher);

    void update(Teacher teacher);

    void delete(Teacher teacher);

    List findByName(String nameTeacher);

    List findByDate(LocalDate birthday);

    List selectTeachers(Map<String, Object> parameters);

    String selectTeachers(String filter);

    List getTeachersForGroup(Map<String, Object> parameters);

    String getTeachersForGroup(int groupId, String filter);

    List getNewTeachersForGroup(Map<String, Object> parameters);

    String getNewTeachersForGroup(int groupId, String filter);
}
