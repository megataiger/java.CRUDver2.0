package workWithBase.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.GroupSerialize;
import gsonSerialize.StudentEasySerialize;
import gsonSerialize.TeacherEasySerialize;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.daoInterfaces.GroupDAOInterface;
import workWithBase.daoInterfaces.StudentDAOInterface;
import workWithBase.daoInterfaces.TeacherDAOInterface;

import java.util.List;

@Service
public class GroupService {

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Group.class, new GroupSerialize()).create();
    private Gson gsonEasyTeacher = new GsonBuilder()
            .registerTypeAdapter(Teacher.class, new TeacherEasySerialize())
            .create();
    private Gson gsonEasyStudent =
            new GsonBuilder()
                    .registerTypeAdapter(Student.class, new StudentEasySerialize())
                    .create();

    private GroupDAOInterface groupDAO;
    private TeacherDAOInterface teacherDAO;
    private StudentDAOInterface studentDAO;

    @Autowired
    public GroupService(GroupDAOInterface groupDAO,
                        TeacherDAOInterface teacherDAO,
                        StudentDAOInterface studentDAO){
        this.groupDAO = groupDAO;
        this.teacherDAO = teacherDAO;
        this.studentDAO = studentDAO;
    }

    @Transactional
    public void setNumber(int id, int number) {
        Group group = groupDAO.findById(id);
        group.set(number);
        groupDAO.update(group);
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

    @Transactional
    public String getGroups(int page, int length, String search, String orderBy, String draw) {

        JsonObject result = new JsonObject();

        List<Group> groups = groupDAO.getGroups(search, page, length, orderBy);

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(groups));
        result.addProperty("recordsTotal", groupDAO.getLengthTable());
        result.addProperty("recordsFiltered", groupDAO.getGroups(search));

        return result.toString();
    }

    @Transactional
    public String getStudents(int numberGroup, int page, int length,
                                   String orderBy, String filter, String draw) {

        JsonObject dataStudents = new JsonObject();

        int groupId = groupDAO.selectGroupByNumber(numberGroup).getId();

        List<Student> students = studentDAO.findByGroup(groupId, page, length, orderBy, filter);

        dataStudents.addProperty("draw", draw);
        dataStudents.add("data", gsonEasyStudent.toJsonTree(students));
        dataStudents.addProperty("recordsTotal", studentDAO.findByGroup(groupId));
        dataStudents.addProperty("recordsFiltered", studentDAO.findByGroup(groupId, filter));

        return dataStudents.toString();
    }

    @Transactional
    public String getTeachers(int number, int page, int length,
                                   String search, String orderBy, String draw){
        int groupId = groupDAO.selectGroupByNumber(number).getId();

        JsonObject result = new JsonObject();
        List<Teacher> teachers = teacherDAO.getTeachersForGroup(groupId, page, length, orderBy, search);

        result.addProperty("draw", draw);
        result.add("data", gsonEasyTeacher.toJsonTree(teachers));
        result.addProperty("recordsTotal", teacherDAO.getTeachersForGroup(groupId, ""));
        result.addProperty("recordsFiltered", teacherDAO.getTeachersForGroup(groupId, search));

        return result.toString();
    }

    @Transactional
    public String getNewTeachers(int number, int page, int length,
                                      String search, String orderBy, String draw){
        int groupId = groupDAO.selectGroupByNumber(number).getId();

        JsonObject result = new JsonObject();
        List<Teacher> teachers = teacherDAO.getNewTeachersForGroup(groupId, page, length, orderBy, search);

        result.addProperty("draw", draw);
        result.add("data", gsonEasyTeacher.toJsonTree(teachers));
        result.addProperty("recordsTotal", teacherDAO.getNewTeachersForGroup(groupId, ""));
        result.addProperty("recordsFiltered", teacherDAO.getNewTeachersForGroup(groupId, search));

        return result.toString();
    }
}