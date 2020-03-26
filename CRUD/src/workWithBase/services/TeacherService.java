package workWithBase.services;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.repositories.TeacherRepository;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

@Service
public class TeacherService implements TeacherServiceInterface {

    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher findById(int id) {
        return teacherRepository.findById(id).orElse(null);
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
    public void addGroup(Group group, Teacher teacher) {
        teacherRepository.insertRecord(group.getId(), teacher.getId());
    }

    @Transactional
    public void deleteGroup(Group group, Teacher teacher) {
        teacherRepository.deleteRecord(group.getId(), teacher.getId());
    }
}