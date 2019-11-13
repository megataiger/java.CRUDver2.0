package workWithBase.daoInterfaces;

import objectForStrokeBase.Teacher;
import java.time.LocalDate;
import java.util.List;

public interface TeacherDAOInterface {
    Teacher findById(int idTeacher);

    List getAll();

    List findByName(String nameTeacher);

    List findByDate(LocalDate birthday);

    void save(Teacher teacher);

    void update(Teacher teacher);

    void delete(Teacher teacher);
}
