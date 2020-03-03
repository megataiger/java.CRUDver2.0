package workWithBase.services;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.daoInterfaces.GroupDAOInterface;
import workWithBase.daoInterfaces.TeacherDAOInterface;
import workWithBase.serviceInterfaces.GroupServiceInterface;

import java.util.List;
import java.util.Map;

@Service
public class GroupService implements GroupServiceInterface {

    private GroupDAOInterface groupDAO;
    private TeacherDAOInterface teacherDAO;

    @Autowired
    public GroupService(GroupDAOInterface groupDAO,
                        TeacherDAOInterface teacherDAO){
        this.groupDAO = groupDAO;
        this.teacherDAO = teacherDAO;
    }

    @Transactional
    public void insert(int number) {
        Group group = new Group(number);
        groupDAO.save(group);
    }

    @Transactional
    public void delete(int id) {
        Group group = groupDAO.findById(id);
        groupDAO.delete(group);
    }

    @Transactional
    public void setNumber(int id, int number) {
        Group group = groupDAO.findById(id);
        group.set(number);
        groupDAO.update(group);
    }

    @Transactional
    public void addTeacher(int number, int idTeacher) {
        Teacher teacher = teacherDAO.findById(idTeacher);
        Group group = groupDAO.selectGroupByNumber(number);

        group.addTeacher(teacher);
        groupDAO.update(group);
    }

    @Transactional
    public void deleteTeacher(int number, int idTeacher) {
        Teacher teacher = teacherDAO.findById(idTeacher);
        Group group = groupDAO.selectGroupByNumber(number);

        group.removeTeacher(teacher);
        groupDAO.update(group);
    }

    public List<Group> getGroups(Map<String, Object> parameters) {
        return groupDAO.getGroups(parameters);
    }

    public Group getByNumber(int number) {
        return groupDAO.selectGroupByNumber(number);
    }

    public String getGroupsLength(String filter) {
        return groupDAO.getGroups(filter);
    }

    public List<Group> getGroupsForTeacher(Map<String, Object> parameters){
        return groupDAO.getGroupForTeacher(parameters);
    }

    public String getGroupsForTeacherLength(int idTeacher, String filter){
        return groupDAO.getGroupForTeacher(idTeacher, filter);
    }

    public List<Group> getNewGroupsForTeacher(Map<String, Object> parameters){
        return groupDAO.getNewGroupForTeacher(parameters);
    }

    public String getNewGroupsForTeacherLength(int idTeacher, String filter){
        return groupDAO.getNewGroupForTeacher(idTeacher, filter);
    }
}