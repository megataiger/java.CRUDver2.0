package objectForStrokeBase;

import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column (name = "birthday")
    private LocalDate date;

    @Enumerated (EnumType.STRING)
    @Column (name = "gender")
    private Gender gender;

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinTable(name = "group_teacher",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")}
            )
    List<Group> groups = new ArrayList<>();



    public Teacher() {
    }

    public Teacher(int idTeacher, String nameTeacher, LocalDate birthday, Gender gender) {
        id = idTeacher;
        name = nameTeacher;
        date = birthday;
        this.gender = gender;
    }

    public Teacher(String nameTeacher, LocalDate birthday, Gender gender) {
        name = nameTeacher;
        date = birthday;
        this.gender = gender;
    }



    public String toString() {
        return id + "\t" + name + "\t" + date + "\t" + gender;
    }

    public void setNameTeacher(String newName) {
        name = newName;
    }

    public void setBirthdayTeacher(LocalDate newDate) {
        date = newDate;
    }

    public void setGenderTeacher(Gender newGender) {
        gender = newGender;
    }



    public String getName() {
        return name;
    }

    public String getDate() {
        return date.getYear() + "-" + date.getMonthValue() +
                "-" + date.getDayOfMonth();
    }

    public String getGender() {
        return gender.toString();
    }

    public int getId() {
        return id;
    }

    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public boolean equals(Object obj) {
        Teacher teacher = (Teacher) obj;
        if(id == teacher.id) {
            return true;
        } else {
            return false;
        }
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public void removeAllGroups() {
        groups = null;
    }


    public static void main(String[] args) {
        Teacher teacher = new TeacherDAO().findById(27);
        teacher.removeAllGroups();
        TeacherDAO tc = new TeacherDAO();
        tc.delete(teacher);
    }
}
