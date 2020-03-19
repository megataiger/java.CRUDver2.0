package workWithBase.services;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.repositories.GroupRepository;
import workWithBase.serviceInterfaces.GroupServiceInterface;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

@Service
public class GroupService implements GroupServiceInterface {

    private GroupRepository groupRepository;
    private TeacherServiceInterface teacherService;

    @Autowired
    public GroupService(GroupRepository groupRepository,
                        TeacherServiceInterface teacherService){
        this.groupRepository = groupRepository;
        this.teacherService = teacherService;
    }

    public Group findById(int id) {
        return groupRepository.findById(id).orElse(new Group());
    }

    public void save(Group group) {
        groupRepository.save(group);
    }

    public void delete(Group group) {
        groupRepository.delete(group);
    }

    public int getCount() {
        return (int) groupRepository.count();
    }

    @Transactional
    public void addTeacher(int groupId, int teacherId) {
        Group group = findById(groupId);
        Teacher teacher = teacherService.findById(teacherId);
        group.addTeacher(teacher);
        save(group);
    }

    @Transactional
    public void removeTeacher(int groupId, int teacherId){
        Group group = findById(groupId);
        Teacher teacher = teacherService.findById(teacherId);
        group.removeTeacher(teacher);
        save(group);
    }

    public Page<Group> getGroups(String filter, Pageable pageable) {
        return groupRepository.getAll(filter, pageable);
    }

    public Page<Group> getGroupsInTeacher(int teacherId, String filter, Pageable pageable){
        return groupRepository.getGroupInTeacher(teacherId, filter, pageable);
    }

    public int getCountGroupInTeacher(int idTeacher) {
        return groupRepository.getCountGroupInTeacher(idTeacher);
    }

    public Page<Group> getGroupNotInTeacher(int teacherId, String filter, Pageable pageable){
        return groupRepository.getGroupNotInTeacher(teacherId, filter, pageable);
    }

    public int getCountGroupNotInTeacher(int idTeacher) {
        return groupRepository.getCountGroupNotInTeacher(idTeacher);
    }
}