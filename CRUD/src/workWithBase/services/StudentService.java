package workWithBase.services;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.daoInterfaces.GroupDAOInterface;
import workWithBase.daoInterfaces.StudentDAOInterface;
import workWithBase.serviceInterfaces.StudentServiceInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class StudentService implements StudentServiceInterface {

    private StudentDAOInterface studentDAO;
    private GroupDAOInterface groupDAO;

    @Autowired
    public StudentService(StudentDAOInterface studentDAO,
                          GroupDAOInterface groupDAO) {
        this.groupDAO = groupDAO;
        this.studentDAO = studentDAO;
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

    public List<Student> getStudents(Map<String, Object> parameters) {
        return studentDAO.findByFilter(parameters);
    }

    public String getStudents(String filter) {
        return studentDAO.findByFilter(filter);
    }

    public List<Group> getPromptGroups(int number) {
        return groupDAO.searchGroup(number);
    }

    public List<Student> getGroupStudents(Map<String, Object> parameters) {
        return studentDAO.findByGroup(parameters);
    }

    public String getGroupStudentsLength(int idGroup, String filter) {
        return studentDAO.findByGroup(idGroup, filter);
    }
}