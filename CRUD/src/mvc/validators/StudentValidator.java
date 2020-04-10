package mvc.validators;

import objectForStrokeBase.Student;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class StudentValidator implements Validator {
    public boolean supports(Class clazz) {
        return Student.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        Student student = (Student) obj;
        if(student.getId() == 0) {
            e.reject("id", "Студента не сущетсвует или был удалён");
        }
    }
}