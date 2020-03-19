package mvc.converters;

import objectForStrokeBase.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import workWithBase.serviceInterfaces.StudentServiceInterface;

public class StudentConverter implements Converter<String, Student>{
    private StudentServiceInterface studentService;

    @Autowired
    public StudentConverter(StudentServiceInterface studentService) {
        this.studentService = studentService;
    }

    @Override
    public Student convert(String studentId) {
        int id = Integer.parseInt(studentId);
        return studentService.findById(id);
    }
}