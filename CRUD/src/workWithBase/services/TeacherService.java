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

import java.time.LocalDate;
import java.util.List;

@Service
public class TeacherService {

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

    @Transactional
    public void setName(int id, String newName) {
        Teacher teacher = teacherDAO.findById(id);
        teacher.setNameTeacher(newName);
        teacherDAO.update(teacher);
    }

    @Transactional
    public void setBirthday(int id, LocalDate birthday) {
        Teacher teacher = teacherDAO.findById(id);
        teacher.setBirthdayTeacher(birthday);
        teacherDAO.update(teacher);
    }

    @Transactional
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

    @Transactional
    public void insertTeacher(String name, LocalDate birthday, Gender gender) {
        Teacher teacher = new Teacher(name, birthday, gender);
        teacherDAO.save(teacher);
    }

    @Transactional
    public void deleteTeacher(int id) {
        Teacher teacher = teacherDAO.findById(id);
        teacherDAO.delete(teacher);
    }

    @Transactional
    public String getTeachers(int page, int length, String search, String orderBy, String draw) {
        List<Teacher> teachers = teacherDAO.selectTeachers(page, length, search, orderBy);
        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(teachers));
        result.addProperty("recordsTotal", teacherDAO.selectTeachers(""));
        result.addProperty("recordsFiltered", teacherDAO.selectTeachers(search));

        return result.toString();
    }

    @Transactional
    public String getGroups(int idTeacher, int page, int length, String search,
                            String orderBy, String draw) {
        List<Group> groups = groupDAO.getGroupForTeacher(idTeacher, page, length, orderBy, search);

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gsonGroup.toJsonTree(groups));
        result.addProperty("recordsTotal", groupDAO.getGroupForTeacher(idTeacher, ""));
        result.addProperty("recordsFiltered", groupDAO.getGroupForTeacher(idTeacher, search));

        return result.toString();
    }

    @Transactional
    public String getNewGroups(int idTeacher, int page, int length, String search,
                            String orderBy, String draw) {
        List<Group> groups = groupDAO.getNewGroupForTeacher(idTeacher, page, length, orderBy, search);

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gsonGroup.toJsonTree(groups));
        result.addProperty("recordsTotal", groupDAO.getNewGroupForTeacher(idTeacher, ""));
        result.addProperty("recordsFiltered", groupDAO.getNewGroupForTeacher(idTeacher, search));

        return result.toString();
    }

    @Transactional
    public void addGroup(int idTeacher, int numberGroup) {
        Teacher teacher = teacherDAO.findById(idTeacher);
        Group group = groupDAO.selectGroupByNumber(numberGroup);

        teacher.addGroup(group);
        teacherDAO.update(teacher);
    }

    @Transactional
    public void deleteGroup(int idTeacher, int numberGroup) {
        Teacher teacher = teacherDAO.findById(idTeacher);
        Group group = groupDAO.selectGroupByNumber(numberGroup);

        teacher.removeGroup(group);
        teacherDAO.update(teacher);
    }
}