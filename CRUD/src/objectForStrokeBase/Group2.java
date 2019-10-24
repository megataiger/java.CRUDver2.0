package objectForStrokeBase;

import workWithBase.GroupBase;

import java.sql.SQLException;

public class Group2 {
    private int id;
    private int number;
    private GroupBase in = new GroupBase();



    public Group2() {
    }

    public Group2(int idGroup, int numberGroup) {
        id = idGroup;
        number = numberGroup;
    }

    public Group2(int numberGroup) {
        number = numberGroup;
    }


    public boolean equals(Group2 group) {
        if (number == group.number) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return id + "\t" + number;
    }

    public void get() throws SQLException {
        in.select();
    }

    public void getById(int idGroup) throws SQLException {
        Group2 group = in.selectGroupById(idGroup);
        id = group.id;
        number = group.number;
        System.out.println(this);
    }

    public void getByNumber(int numberGroup) throws SQLException {
        Group2 group = in.selectGroupByNumber(numberGroup);
        id = group.id;
        number = group.number;
    }

    public void add() throws SQLException {
        in.insert(this);
    }

    public void set(int newNumber) throws SQLException {
        number = newNumber;
        in.update(this);
    }

    public void remove() throws SQLException {
        in.delete(this);
    }

    public void viewStudents() throws SQLException {
        in.selectStudent(this);
    }

    public void viewTeachers() throws SQLException {
        in.selectTeacher(this);
    }

    public void addTeacher(int idTeacher) throws SQLException {
        in.insertTeacher(this, idTeacher);
    }

    public void setTeacher(int oldTeacherId, int newTeacherId)
            throws SQLException {
        in.updateTeacher(this, oldTeacherId, newTeacherId);
    }

    public void removeTeacher(int idTeacher) throws SQLException {
        in.deleteTeacher(this, idTeacher);
    }



    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }
}