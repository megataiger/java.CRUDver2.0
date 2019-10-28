package objectForStrokeBase;

import workWithBase.classes.StudentBase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private LocalDate date;
    private Gender gender;
    private int group;
    private StudentBase in = new StudentBase();



    public Student() {}

    public Student(int idStudent, String nameStudent, LocalDate birthday, Gender gender,
                   int groupId) {
        id = idStudent;
        name = nameStudent;
        date = birthday;
        this.gender = gender;
        group = groupId;
    }

    public Student(String nameStudent, LocalDate birthday, Gender gender, int groupId) {
        name = nameStudent;
        date = birthday;
        this.gender = gender;
        group = groupId;
    }



    public String toString() {
        return id + "\t" + name + "\t" + date + "\t" + gender.getValue() +
                "\t" + group;
    }

    public void get(int idStudent) throws SQLException {
        Student student = in.selectStudent(idStudent);
        id = student.id;
        name = student.name;
        date = student.date;
        gender = student.gender;
        group = student.group;
        System.out.println(this);
    }

    public List<Student> get(int day, int month, int year) throws SQLException {
        LocalDate date = LocalDate.of(year, month, day);
        return in.selectStudent(date);
    }

    public void setNameStudent(String newName) throws SQLException {
        name = newName;
    }

    public void setBirthdayStudent(LocalDate newDate) throws SQLException {
        date = newDate;
    }

    public void setGenderStudent(Gender newGender) throws SQLException {
        gender = newGender;
    }

    public void setGroupStudent(int newGroup) throws SQLException {
        group = newGroup;
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

    public int getGroupId() {
        return group;
    }

    public int getId() {
        return id;
    }
}
