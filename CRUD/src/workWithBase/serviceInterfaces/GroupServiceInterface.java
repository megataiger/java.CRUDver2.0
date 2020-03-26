package workWithBase.serviceInterfaces;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupServiceInterface {
    Group findById(int id);

    void save(Group group);

    void delete(Group group);

    int getCount();

    Page<Group> getGroups(String filter, Pageable pageable);

    Page<Group> getGroupsInTeacher(int teacherId,
                                   String filter,
                                   Pageable pageable);

    Page<Group> getGroupNotInTeacher(int teacherId,
                                     String filter,
                                     Pageable pageable);

    int getCountGroupInTeacher(int idTeacher);

    int getCountGroupNotInTeacher(int idTeacher);

    void addTeacher(Group group, Teacher teacher);

    void removeTeacher(Group group, Teacher teacher);
}