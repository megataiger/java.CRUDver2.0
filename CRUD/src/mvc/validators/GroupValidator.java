package mvc.validators;

import objectForStrokeBase.Group;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class GroupValidator implements Validator {
    public boolean supports(Class clazz) {
        return Group.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        Group student = (Group) obj;
        if(student.getId() == 0) {
            e.reject("id", "Группы не сущетсвует или была удалена");
        }
    }
}