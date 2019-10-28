package objectForStrokeBase;

import java.time.LocalDate;

public class Student {
    private int id;
    private String name;
    private LocalDate date;
    private Gender gender;
    private int group;



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

    public void setNameStudent(String newName) {
        name = newName;
    }

    public void setBirthdayStudent(LocalDate newDate) {
        date = newDate;
    }

    public void setGenderStudent(Gender newGender) {
        gender = newGender;
    }

    public void setGroupStudent(int newGroup) {
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
