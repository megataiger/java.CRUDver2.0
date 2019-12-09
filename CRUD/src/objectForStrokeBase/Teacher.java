package objectForStrokeBase;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    private List<Group> groups = new ArrayList<>();



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

    public void setGroup(Group oldGroup, Group newGroup) {
        groups.remove(oldGroup);
        groups.add(newGroup);
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

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id &&
                name.equals(teacher.name) &&
                date.equals(teacher.date) &&
                gender == teacher.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, gender, groups);
    }
}
