package workWithBase.services;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.repositories.GroupRepository;
import workWithBase.repositories.TeacherRepository;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

@Service
public class TeacherService implements TeacherServiceInterface {

    private TeacherRepository teacherRepository;
    private GroupRepository groupRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository,
                          GroupRepository groupRepository) {
        this.teacherRepository = teacherRepository;
        this.groupRepository = groupRepository;
    }

    public Teacher findById(int id) {
        return teacherRepository.findById(id).orElse(new Teacher());
    }

    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void delete(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public int getCount() {
        return (int) teacherRepository.count();
    }

    public Page<Teacher> getTeachers(String filter, Pageable pageable) {
        return teacherRepository.getAll(filter, pageable);
    }

    public Page<Teacher> getTeacherInGroup(int groupId,
                                           String filter,
                                           Pageable pageable) {
        return teacherRepository.getTeacherInGroup(groupId, filter, pageable);
    }

    public int getCountTeacherInGroup(int groupId) {
        return teacherRepository.getCountTeacherInGroup(groupId);
    }

    public Page<Teacher> getTeacherNotInGroup(int groupId,
                                              String filter,
                                              Pageable pageable) {
        return teacherRepository.getTeacherNotInGroup(groupId, filter, pageable);
    }

    public int getCountTeacherNotInGroup(int groupId){
        return teacherRepository.getCountTeacherNotInGroup(groupId);
    }

    @Transactional
    public void addGroup(int teacherId, int groupId) {
        Teacher teacher = findById(teacherId);
        Group group = groupRepository.findById(groupId).orElse(new Group());
        teacher.addGroup(group);
        save(teacher);
    }

    @Transactional
    public void deleteGroup(int teacherId, int groupId) {
        Teacher teacher = findById(teacherId);
        Group group = groupRepository.findById(groupId).orElse(new Group());
        teacher.removeGroup(group);
        save(teacher);
    }
}