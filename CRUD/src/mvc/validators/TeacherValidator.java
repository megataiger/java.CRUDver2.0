package mvc.validators;

import objectForStrokeBase.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class TeacherValidator implements Validator {
    public boolean supports(Class clazz) {
        return Teacher.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        Teacher student = (Teacher) obj;
        if(student.getId() == 0) {
            e.reject("id", "Преподавателя не сущетсвует или был удалён");
        }
    }
}