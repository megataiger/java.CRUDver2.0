package workWithBase.serviceInterfaces;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherServiceInterface {
    Teacher findById(int id);

    void save(Teacher teacher);

    void delete(Teacher teacher);

    int getCount();

    Page<Teacher> getTeachers(String filter, Pageable pageable);

    Page<Teacher> getTeacherInGroup(int groupId,
                                    String filter,
                                    Pageable pageable);

    int getCountTeacherInGroup(int groupId);

    Page<Teacher> getTeacherNotInGroup(int groupId,
                                       String filter,
                                       Pageable pageable);

    int getCountTeacherNotInGroup(int groupId);

    void addGroup(Group group, Teacher teacher);

    void deleteGroup(Group group, Teacher teacher);
}