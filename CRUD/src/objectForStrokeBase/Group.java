package objectForStrokeBase;

import workWithBase.classes.GroupBase;

import java.sql.SQLException;

public class Group {
    private int id;
    private int number;
    private GroupBase in = new GroupBase();



    public Group() {
    }

    public Group(int idGroup, int numberGroup) {
        id = idGroup;
        number = numberGroup;
    }

    public Group(int numberGroup) {
        number = numberGroup;
    }


    public boolean equals(Group group) {
        if (number == group.number) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return id + "\t" + number;
    }

    public void getById(int idGroup) throws SQLException {
        Group group = in.selectGroupById(idGroup);
        id = group.id;
        number = group.number;
        System.out.println(this);
    }

    public void getByNumber(int numberGroup) throws SQLException {
        Group group = in.selectGroupByNumber(numberGroup);
        id = group.id;
        number = group.number;
    }

    public void set(int newNumber) throws SQLException {
        number = newNumber;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }
}