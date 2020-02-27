package workWithBase.serviceInterfaces;

import objectForStrokeBase.Gender;
import java.time.LocalDate;
import java.util.Map;

public interface StudentServiceInterface {
    void insert(String name, LocalDate birthday, Gender gender, int numberGroup);

    void delete(int id);

    void updateName(int id, String newName);

    void updateBirthday(int id, LocalDate birthday);

    void updateGender(int id, String newGender);

    void updateGroup(int id, int numberGroup);

    String getStudents(Map<String, Object> parameters);

    String getPromptGroups(int number);
}