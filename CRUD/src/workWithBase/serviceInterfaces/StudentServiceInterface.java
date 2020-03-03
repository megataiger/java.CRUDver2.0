package workWithBase.serviceInterfaces;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StudentServiceInterface {
    void insert(String name, LocalDate birthday, Gender gender, int numberGroup);

    void delete(int idStudent);

    void updateName(int idStudent, String newName);

    void updateBirthday(int idStudent, LocalDate birthday);

    void updateGender(int idStudent, String newGender);

    void updateGroup(int idStudent, int numberGroup);

    List<Student> getStudents(Map<String, Object> parameters);

    String getStudents(String filter);

    List<Group> getPromptGroups(int number);

    List<Student> getGroupStudents(Map<String, Object> parameters);

    String getGroupStudentsLength(int number, String filter);
}