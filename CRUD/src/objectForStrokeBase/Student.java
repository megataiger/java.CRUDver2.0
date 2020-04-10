package objectForStrokeBase;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private LocalDate date;

    @Expose
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Expose
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "group_id")
    private Group group;

    public Student() {
    }

    public Student(String name, LocalDate date, Gender gender, Group group) {
        this.name = name;
        this.date = date;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGroup(Group group) {
        this.group = group;
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
}

