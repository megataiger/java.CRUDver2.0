package workWithBase.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.GroupSerialize;
import gsonSerialize.TeacherSerialize;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.daoInterfaces.GroupDAOInterface;
import workWithBase.daoInterfaces.TeacherDAOInterface;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TeacherService implements TeacherServiceInterface {

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Teacher.class, new TeacherSerialize())
            .create();
    private Gson gsonGroup = new GsonBuilder()
            .registerTypeAdapter(Group.class, new GroupSerialize())
            .create();

    private TeacherDAOInterface teacherDAO;
    private GroupDAOInterface groupDAO;

    @Autowired
    public TeacherService(TeacherDAOInterface teacherDAO,
                          GroupDAOInterface groupDAO) {
        this.teacherDAO = teacherDAO;
        this.groupDAO = groupDAO;
    }

    public void insert(String name, LocalDate birthday, Gender gender) {
        Teacher teacher = new Teacher(name, birthday, gender);
        teacherDAO.save(teacher);
    }

    public void delete(int id) {
        Teacher teacher = teacherDAO.findById(id);
        teacherDAO.delete(teacher);
    }


    public void setName(int id, String newName) {
        Teacher teacher = teacherDAO.findById(id);
        teacher.setNameTeacher(newName);
        teacherDAO.update(teacher);
    }

    public void setBirthday(int id, LocalDate birthday) {
        Teacher teacher = teacherDAO.findById(id);
        teacher.setBirthdayTeacher(birthday);
        teacherDAO.update(teacher);
    }

    public void setGender(int id, String gender) {
        Teacher teacher = teacherDAO.findById(id);
        if (gender != null) {
            for (Gender e : Gender.values()) {
                if (gender.equals(e.toString())) {
                    teacher.setGenderTeacher(e);
                }
            }
        }
        teacherDAO.update(teacher);
    }

    public String getTeachers(Map<String, Object> parameters) {
        String draw = (String) parameters.get("draw");
        String filter = (String) parameters.get("filter");

        List teachers = teacherDAO.selectTeachers(parameters);
        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(teachers));
        result.addProperty("recordsTotal", teacherDAO.selectTeachers(""));
        result.addProperty("recordsFiltered", teacherDAO.selectTeachers(filter));

        return result.toString();
    }

    public String getGroups(Map<String, Object> parameters) {
        String draw = (String) parameters.get("draw");
        String filter = (String) parameters.get("filter");
        int teacherId = (Integer) parameters.get("teacherId");

        List groups = groupDAO.getGroupForTeacher(parameters);

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gsonGroup.toJsonTree(groups));
        result.addProperty("recordsTotal", groupDAO.getGroupForTeacher(teacherId, ""));
        result.addProperty("recordsFiltered", groupDAO.getGroupForTeacher(teacherId, filter));

        return result.toString();
    }

    public String getNewGroups(Map<String, Object> parameters) {
        String draw = (String) parameters.get("draw");
        String filter = (String) parameters.get("filter");
        int teacherId = (Integer) parameters.get("teacherId");

        List groups = groupDAO.getNewGroupForTeacher(parameters);

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gsonGroup.toJsonTree(groups));
        result.addProperty("recordsTotal", groupDAO.getNewGroupForTeacher(teacherId, ""));
        result.addProperty("recordsFiltered", groupDAO.getNewGroupForTeacher(teacherId, filter));

        return result.toString();
    }

    public void addGroup(int idTeacher, int numberGroup) {
        Teacher teacher = teacherDAO.findById(idTeacher);
        Group group = groupDAO.selectGroupByNumber(numberGroup);

        teacher.addGroup(group);
        teacherDAO.update(teacher);
    }

    public void deleteGroup(int idTeacher, int numberGroup) {
        Teacher teacher = teacherDAO.findById(idTeacher);
        Group group = groupDAO.selectGroupByNumber(numberGroup);

        teacher.removeGroup(group);
        teacherDAO.update(teacher);
    }
}