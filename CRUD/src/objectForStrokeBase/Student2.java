package objectForStrokeBase;

import workWithBase.StudentBase;

import java.sql.SQLException;
import java.time.LocalDate;

public class Student2 {
    private int id;
    private String name;
    private LocalDate date;
    private Gender gender;
    private int group;
    private StudentBase in = new StudentBase();



    public Student2() {}

    public Student2(int idStudent, String nameStudent, LocalDate birthday, Gender gender,
                    int groupId) {
        id = idStudent;
        name = nameStudent;
        date = birthday;
        this.gender = gender;
        group = groupId;
    }

    public Student2(String nameStudent, LocalDate birthday, Gender gender, int groupId) {
        name = nameStudent;
        date = birthday;
        this.gender = gender;
        group = groupId;
    }



    public String toString() {
        return id + "\t" + name + "\t" + date + "\t" + gender.getValue() +
                "\t" + group;
    }

    public void get() throws SQLException {
        in.selectStudent();
    }

    public void get(int idStudent) throws SQLException {
        Student2 student = in.selectStudent(idStudent);
        id = student.id;
        name = student.name;
        date = student.date;
        gender = student.gender;
        group = student.group;
        System.out.println(this);
    }

    public void get(String nameStudent) throws SQLException {
        in.selectStudent(nameStudent);
    }

    public void get(int day, int month, int year) throws SQLException {
        LocalDate date = LocalDate.of(year, month, day);
        in.selectStudent(date);
    }

    public void viewGroupSteudent(int idGroup) throws SQLException {
        in.selectGroup(idGroup);
    }

    public void add() throws SQLException {
        in.insert(this);
    }

    public void setNameStudent(String newName) throws SQLException {
        name = newName;
        in.update(this);
    }

    public void setBirthdayStudent(LocalDate newDate) throws SQLException {
        date = newDate;
        in.update(this);
    }

    public void setGenderStudent(Gender newGender) throws SQLException {
        gender = newGender;
        in.update(this);
    }

    public void setGroupStudent(int newGroup) throws SQLException {
        group = newGroup;
        in.update(this);
    }

    public void remove() throws SQLException {
        in.delete(this);
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
