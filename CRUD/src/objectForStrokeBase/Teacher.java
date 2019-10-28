package objectForStrokeBase;

import workWithBase.classes.TeacherBase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Teacher {
    private int id;
    private String name;
    private LocalDate date;
    private Gender gender;
    private TeacherBase in = new TeacherBase();


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
        return id + "\t" + name + "\t" + date + "\t" + gender.getValue();
    }

    public void get(int idTeacher) throws SQLException {
        Teacher teacher = in.selectTeacher(idTeacher);
        id = teacher.id;
        name = teacher.name;
        date = teacher.date;
        gender = teacher.gender;
        System.out.println(this);
    }

    public List<Teacher> get(int day, int month, int year) throws SQLException {
        LocalDate date = LocalDate.of(year, month, day);
        return in.selectTeacher(date);
    }

    public void setNameTeacher(String newName) {
        name = newName;
    }

    public void setBirthdayTeacher(LocalDate newDate) throws SQLException {
        date = newDate;
    }

    public void setGenderTeacher(Gender newGender) throws SQLException {
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
        return gender.getValue();
    }

    public int getId() {
        return id;
    }
}
