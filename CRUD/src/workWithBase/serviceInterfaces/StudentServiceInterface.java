package workWithBase.serviceInterfaces;

import objectForStrokeBase.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentServiceInterface {
    Student findById(int id);

    void insert(Student student);

    void update(Student student);

    void delete(Student student);

    int getCount();

    Page<Student> getStudents(String filter, Pageable pageable);

    Page<Student> getGroupStudents(int groupId,
                                   String filter,
                                   Pageable pageable);

    int getCountGroupStudents(int groupId);
}