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

@Service
public class GroupService implements GroupServiceInterface {

    private GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public Group findById(int id) {
        return groupRepository.findById(id).orElse(null);
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
    public void addTeacher(Group group, Teacher teacher) {
        groupRepository.insertRecord(group.getId(), teacher.getId());
    }

    @Transactional
    public void removeTeacher(Group group, Teacher teacher) {
        groupRepository.deleteRecord(group.getId(), teacher.getId());
    }

    public Page<Group> getGroups(String filter, Pageable pageable) {
        return groupRepository.getAll(filter, pageable);
    }

    public Page<Group> getGroupsInTeacher(int teacherId,
                                          String filter,
                                          Pageable pageable){
        return groupRepository.getGroupInTeacher(teacherId, filter, pageable);
    }

    public int getCountGroupInTeacher(int idTeacher) {
        return groupRepository.getCountGroupInTeacher(idTeacher);
    }

    public Page<Group> getGroupNotInTeacher(int teacherId,
                                            String filter,
                                            Pageable pageable){
        return groupRepository.getGroupNotInTeacher(teacherId, filter, pageable);
    }

    public int getCountGroupNotInTeacher(int teacherId) {
        return groupRepository.getCountGroupNotInTeacher(teacherId);
    }
}