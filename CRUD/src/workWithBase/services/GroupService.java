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
import workWithBase.serviceInterfaces.GroupServiceInterface;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GroupService implements GroupServiceInterface {

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

    public void insert(int number) {
        Group group = new Group(number);
        groupDAO.save(group);
    }

    public void delete(int id) {
        Group group = groupDAO.findById(id);
        groupDAO.delete(group);
    }

    public void setNumber(int id, int number) {
        Group group = groupDAO.findById(id);
        group.set(number);
        groupDAO.update(group);
    }

    public void addTeacher(int number, int idTeacher) {
        Teacher teacher = teacherDAO.findById(idTeacher);
        Group group = groupDAO.selectGroupByNumber(number);

        group.addTeacher(teacher);
        groupDAO.update(group);
    }

    public void deleteTeacher(int number, int idTeacher) {
        Teacher teacher = teacherDAO.findById(idTeacher);
        Group group = groupDAO.selectGroupByNumber(number);

        group.removeTeacher(teacher);
        groupDAO.update(group);
    }

    public String getGroups(Map<String, Object> parameters) {
        String draw = (String) parameters.get("draw");
        String filter = (String) parameters.get("filter");

        JsonObject result = new JsonObject();

        List groups = groupDAO.getGroups(parameters);

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(groups));
        result.addProperty("recordsTotal", groupDAO.getGroups(""));
        result.addProperty("recordsFiltered", groupDAO.getGroups(filter));

        return result.toString();
    }

    public String getStudents(Map<String, Object> parameters) {
        String draw = (String) parameters.get("draw");
        String filter = (String) parameters.get("filter");
        int number = (Integer) parameters.get("groupNumber");

        number = groupDAO.selectGroupByNumber(number).getId();
        parameters.put("groupNumber", number);

        JsonObject dataStudents = new JsonObject();

        List students = studentDAO.findByGroup(parameters);

        dataStudents.addProperty("draw", draw);
        dataStudents.add("data",
                gsonEasyStudent.toJsonTree(students));
        dataStudents.addProperty("recordsTotal",
                studentDAO.findByGroup(number, ""));
        dataStudents.addProperty("recordsFiltered",
                studentDAO.findByGroup(number, filter));

        return dataStudents.toString();
    }

    public String getTeachers(Map<String, Object> parameters){
        int number = (Integer) parameters.get("number");
        int groupId = groupDAO.selectGroupByNumber(number).getId();
        parameters.put("groupId", groupId);

        String draw = (String) parameters.get("draw");
        String filter = (String) parameters.get("filter");

        JsonObject result = new JsonObject();
        List teachers = teacherDAO.getTeachersForGroup(parameters);

        result.addProperty("draw", draw);
        result.add("data", gsonEasyTeacher.toJsonTree(teachers));
        result.addProperty("recordsTotal", teacherDAO.getTeachersForGroup(groupId, ""));
        result.addProperty("recordsFiltered", teacherDAO.getTeachersForGroup(groupId, filter));

        return result.toString();
    }

    public String getNewTeachers(Map<String, Object> parameters){
        int number = (Integer) parameters.get("number");
        int groupId = groupDAO.selectGroupByNumber(number).getId();
        parameters.put("groupId", groupId);

        String draw = (String) parameters.get("draw");
        String filter = (String) parameters.get("filter");

        JsonObject result = new JsonObject();
        List teachers = teacherDAO.getNewTeachersForGroup(parameters);

        result.addProperty("draw", draw);
        result.add("data", gsonEasyTeacher.toJsonTree(teachers));
        result.addProperty("recordsTotal", teacherDAO.getNewTeachersForGroup(groupId, ""));
        result.addProperty("recordsFiltered", teacherDAO.getNewTeachersForGroup(groupId, filter));

        return result.toString();
    }
}