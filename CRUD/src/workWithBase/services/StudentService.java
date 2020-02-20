package workWithBase.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import gsonSerialize.StudentSerialize;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.daoInterfaces.GroupDAOInterface;
import workWithBase.daoInterfaces.StudentDAOInterface;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Student.class, new StudentSerialize())
            .create();

    private StudentDAOInterface studentDAO;
    private GroupDAOInterface groupDAO;

    @Autowired
    public StudentService(StudentDAOInterface studentDAO,
                          GroupDAOInterface groupDAO) {
        this.groupDAO = groupDAO;
        this.studentDAO = studentDAO;
    }

    @Transactional
    public Student find(int id) {
        return studentDAO.findById(id);
    }

    @Transactional
    public void insert(String name, LocalDate birthday, Gender gender, int numberGroup) {
        Group group = groupDAO.selectGroupByNumber(numberGroup);
        Student student = new Student(name, birthday, gender, group);
        studentDAO.save(student);
    }

    @Transactional
    public void delete(int id) {
        Student student = studentDAO.findById(id);
        studentDAO.delete(student);
    }

    @Transactional
    public void updateName(int id, String newName) {
        Student student = studentDAO.findById(id);
        if (newName != null) {
            student.setNameStudent(newName);
        }
        studentDAO.update(student);
    }

    @Transactional
    public void updateBirthday(int id, LocalDate birthday) {
        Student student = studentDAO.findById(id);
        if (birthday != null) {
            student.setBirthdayStudent(birthday);
        }
        studentDAO.update(student);
    }

    @Transactional
    public void updateGender(int id, String newGender) {
        Student student = studentDAO.findById(id);
        if (newGender != null) {
            for (Gender e : Gender.values()) {
                if (newGender.equals(e.toString())) {
                    student.setGenderStudent(e);
                }
            }
        }
        studentDAO.update(student);
    }

    @Transactional
    public void updateGroup(int id, int numberGroup) {
        Group newGroup = groupDAO.selectGroupByNumber(numberGroup);
        Student student = studentDAO.findById(id);
        student.setGroupStudent(newGroup);
        studentDAO.update(student);
    }

    @Transactional
    public String getStudents(String search, int page, int length, String order, String draw) {
        List<Student> students = studentDAO.findByFilter(search, page, length, order);

        JsonObject result = new JsonObject();

        result.addProperty("draw", draw);
        result.add("data", gson.toJsonTree(students));
        result.addProperty("recordsTotal", studentDAO.findByFilter(""));
        result.addProperty("recordsFiltered", studentDAO.findByFilter(search));

        return result.toString();
    }
}