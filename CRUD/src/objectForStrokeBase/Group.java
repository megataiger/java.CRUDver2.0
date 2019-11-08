package objectForStrokeBase;

import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "`group`")
public class Group {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY )
    private int id;

    @Column (name = "number")
    private int number;

    @OneToMany (
            mappedBy = "group",
            cascade = CascadeType.DETACH,
            orphanRemoval = true
    )
    private List<Student> students = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_teacher",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id")}
    )
    private List<Teacher> teachers = new ArrayList<>();

    public Group() {
    }

    public Group(int idGroup, int numberGroup) {
        id = idGroup;
        number = numberGroup;
    }



    public Group(int numberGroup) {
        number = numberGroup;
    }

    public String toString() {
        return id + "\t" + number;
    }

    public void set(int newNumber) {
        number = newNumber;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    @Override
    public boolean equals(Object obj) {
        Group group = (Group) obj;
        if(id == group.id) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        GroupDAO tc = new GroupDAO();
        Group group = tc.selectGroupByNumber(441);
        TeacherDAO teachD = new TeacherDAO();
        Teacher teach = teachD.findById(30);
        group.removeTeacher(teach);
        tc.update(group);

    }
}
