package mvc.converters;

import objectForStrokeBase.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.transaction.annotation.Transactional;
import workWithBase.serviceInterfaces.TeacherServiceInterface;

public class TeacherConverter implements Converter<String, Teacher> {
    private TeacherServiceInterface teacherService;

    @Autowired
    public TeacherConverter(TeacherServiceInterface teacherService){
        this.teacherService = teacherService;
    }

    @Override
    @Transactional
    public Teacher convert(String teacherId) {
        int id = Integer.parseInt(teacherId);
        return teacherService.findById(id);
    }
}