package objectForStrokeBase;

import workWithBase.daoClasses.StudentDAO;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

@Entity
@Table (name = "student")
public class Student {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "name")
    private String name;

    @Column (name = "birthday")
    private LocalDate date;

    @Enumerated (EnumType.STRING)
    @Column (name = "gender")
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "group_id")
    private Group group;

    public Student() {
    }

    public Student(String nameStudent, LocalDate birthday, Gender gender, Group group) {
        name = nameStudent;
        date = birthday;
        this.gender = gender;
        this.group = group;
    }



    public String toString() {
        if (group == null) {
            return id + "\t" + name + "\t" + date.toString() + "\t" + gender;
        } else {
            return id + "\t" + name + "\t" + date.toString() + "\t" + gender +
                    "\t" + group.getNumber();
        }
    }

    public void setNameStudent(String newName) {
        name = newName;
    }

    public void setBirthdayStudent(LocalDate newDate) {
        date = newDate;
    }

    public void setGenderStudent(Gender newGender) {
        gender = newGender;
    }

    public void setGroupStudent(Group newGroup) {
        group = newGroup;
    }



    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getGender() {
        return "" + gender;
    }

    public int getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                name.equals(student.name) &&
                date.equals(student.date) &&
                gender == student.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, gender, group);
    }
    public static void main(String[] args) {
        System.out.println(TimeZone.getDefault().getDisplayName());
    }
}

