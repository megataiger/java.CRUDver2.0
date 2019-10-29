package objectForStrokeBase;

import java.time.LocalDate;

public class Student {
    private int id;
    private String name;
    private LocalDate date;
    private Gender gender;
    private Group group;



    public Student() {}

    public Student(int idStudent, String nameStudent, LocalDate birthday, Gender gender,
                   Group group) {
        id = idStudent;
        name = nameStudent;
        date = birthday;
        this.gender = gender;
        this.group = group;
    }

    public Student(String nameStudent, LocalDate birthday, Gender gender, Group group) {
        name = nameStudent;
        date = birthday;
        this.gender = gender;
        this.group = group;
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
        group = new Group (0, newGroup);
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
        return group.getId();
    }

    public int getId() {
        return id;
    }
}
