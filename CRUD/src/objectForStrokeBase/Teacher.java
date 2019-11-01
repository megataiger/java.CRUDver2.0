package objectForStrokeBase;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table (name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column (name = "birthday")
    private LocalDate date;
    @Column (name = "male")
    @Enumerated (EnumType.STRING)
    private Gender gender;



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
}
