package objectForStrokeBase;

import workWithBase.TeacherBase;

import java.sql.SQLException;
import java.time.LocalDate;

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

    public void get() throws SQLException {
        in.selectTeacher();
    }

    public void get(int idTeacher) throws SQLException {
        Teacher teacher = in.selectTeacher(idTeacher);
        id = teacher.id;
        name = teacher.name;
        date = teacher.date;
        gender = teacher.gender;
        System.out.println(this);
    }

    public void get(String nameTeacher) throws SQLException {
        in.selectTeacher(nameTeacher);
    }

    public void get(int day, int month, int year) throws SQLException {
        LocalDate date = LocalDate.of(year, month, day);
        in.selectStudent(date);
    }

    public void add() throws SQLException {
        in.insert(this);
    }

    public void setNameTeacher(String newName) throws SQLException {
        name = newName;
        in.update(this);
    }

    public void setBirthdayTeacher(LocalDate newDate) throws SQLException {
        date = newDate;
        in.update(this);
    }

    public void setGenderTeacher(Gender newGender) throws SQLException {
        gender = newGender;
        in.update(this);
    }

    public void remove() throws SQLException {
        in.delete(this);
    }

    public void viewGroups() throws SQLException {
        in.selectGroups(this);
    }

    public void addGroup(int idGroup) throws SQLException {
        in.insertGroup(this, idGroup);
    }

    public void setGroup(int oldGroupId, int newGroupId)
            throws SQLException {
        in.updateGroup(this, oldGroupId, newGroupId);
    }

    public void removeGroup(int idGroup) throws SQLException {
        in.deleteGroup(this, idGroup);
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
