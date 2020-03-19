package workWithBase.services;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import workWithBase.daoInterfaces.GroupDAOInterface;
import workWithBase.repositories.GroupRepository;
import workWithBase.repositories.StudentRepository;
import workWithBase.serviceInterfaces.StudentServiceInterface;

import java.util.List;

@Service
public class StudentService implements StudentServiceInterface {

    private GroupRepository groupRepository;
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(GroupRepository groupRepository,
                          StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    public Student findById(int id){
        return studentRepository.findById(id).orElse(new Student());
    }

    public void insert(Student student) {
        studentRepository.save(student);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public void update(Student student) {
        studentRepository.save(student);
    }

    public Page<Student> getStudents(String filter, Pageable pageable) {
        return studentRepository.getStudents(filter, pageable);
    }

    public int getCount() {
        return (int) studentRepository.count();
    }

    public List<Group> getPromptGroups(String number) {
        return groupRepository.getPrompt(number);
    }

    public Page<Student> getGroupStudents(int groupId, String filter, Pageable pageable) {
        return studentRepository.findByGroup_Id(groupId, filter, pageable);
    }

    public int getCountGroupStudents(int groupId){
       return (int) studentRepository.getCountGroupStudents(groupId);
    }
}