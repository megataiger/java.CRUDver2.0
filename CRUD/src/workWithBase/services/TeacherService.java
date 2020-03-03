package workWithBase.services;

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
public class TeacherService implements TeacherServiceInterface {

    private TeacherDAOInterface teacherDAO;
    private GroupDAOInterface groupDAO;

    @Autowired
    public TeacherService(TeacherDAOInterface teacherDAO,
                          GroupDAOInterface groupDAO) {
        this.teacherDAO = teacherDAO;
        this.groupDAO = groupDAO;
    }

    @Transactional
    public void insert(String name, LocalDate birthday, Gender gender) {
        Teacher teacher = new Teacher(name, birthday, gender);
        teacherDAO.save(teacher);
    }

    @Transactional
    public void delete(int id) {
        Teacher teacher = teacherDAO.findById(id);
        teacherDAO.delete(teacher);
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

    public List<Teacher> getTeachers(Map<String, Object> parameters) {
        return teacherDAO.getTeachers(parameters);
    }

    public String getTeachersLength(String filter){
        return teacherDAO.getTeachers(filter);
    }

    public List<Teacher> getTeachersForGroup(Map<String, Object> parameters) {
        return teacherDAO.getTeachersForGroup(parameters);
    }

    public String getTeachersForGroupLength(int idGroup, String filter){
        return teacherDAO.getTeachersForGroup(idGroup, filter);
    }

    public List<Teacher> getNewTeachersForGroup(Map<String, Object> parameters) {
        return teacherDAO.getNewTeachersForGroup(parameters);
    }

    public String getNewTeachersForGroupLength(int idGroup, String filter){
        return teacherDAO.getNewTeachersForGroup(idGroup, filter);
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