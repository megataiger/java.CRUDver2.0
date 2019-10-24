package workWithBase;

import objectForStrokeBase.Group;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupBase extends SuperTable {
    private String select = "SELECT * FROM `group`";
    private String insert = "INSERT INTO `group` (id, number) VALUES" +
            "(null, ?)";
    private String update = "UPDATE `group` SET number = ? WHERE id = ?";
    private String delete = "DELETE FROM `group` WHERE id = ?";
    private String selectStudent = "SELECT * FROM student WHERE group_id = ?";
    private String selectTeachers = "SELECT id, name " +
            "FROM `group_teacher` JOIN teacher " +
            "ON `group_teacher`.teacher_id = teacher.id " +
            "WHERE group_id = ?";
    private String insertTeacher = "INSERT INTO `group_teacher` " +
            "(group_id, teacher_id) VALUES (?, ?)";
    private String updateTeacher = "UPDATE `group_teacher` " +
            "SET teacher_id = ? WHERE group_id = ? AND teacher_id = ?";
    private String deleteTeacher = "DELETE FROM `group_teacher` " +
            "WHERE group_id = ? AND teacher_id = ?";


    public Group selectGroupById(int idGroup) throws SQLException {
        select += " WHERE id = ?";
        PreparedStatement prstate = SuperTable.con.prepareStatement(select);
        prstate.setInt(1, idGroup);

        ResultSet result = prstate.executeQuery();

        result.next();
        Group group = recordResult(result);
        return group;
    }

    public Group selectGroupByNumber(int numberGroup) throws SQLException {
        select += " WHERE number = ?";
        PreparedStatement prstate = SuperTable.con.prepareStatement(select);
        prstate.setInt(1, numberGroup);

        ResultSet result = prstate.executeQuery();

        result.next();
        Group group = recordResult(result);
        return group;
    }

    public void select() throws SQLException {
        Statement state = SuperTable.con.createStatement();
        ResultSet result = state.executeQuery(select);
        while (result.next()) {
            System.out.println(recordResult(result));
        }
    }

    public void insert(Group group) throws SQLException {
        PreparedStatement prstate = SuperTable.con.prepareStatement(insert);
        prstate.setInt(1, group.getNumber());
        if(selectGroupByNumber(group.getNumber()).equals(group)) {
            System.out.println("Группа с таким номером уже существует");
        } else {
            prstate.executeUpdate();
        }
    }

    public void update(Group group) throws SQLException {
        PreparedStatement prstate = SuperTable.con.prepareStatement(update);
        prstate.setInt(1, group.getNumber());
        prstate.setInt(2, group.getId());

        prstate.executeUpdate();
    }

    public void delete(Group group) throws SQLException {
        String deleteRel = "DELETE FROM `group_teacher` WHERE group_id = ?";
        PreparedStatement prstate = SuperTable.con.prepareStatement(deleteRel);

        prstate.setInt(1, group.getId());
        prstate.executeUpdate();
        prstate = SuperTable.con.prepareStatement(delete);
        prstate.setInt(1, group.getId());

        prstate.executeUpdate();
    }

    public void selectStudent(Group group) throws SQLException {
        PreparedStatement prstate = SuperTable.con.prepareStatement(selectStudent);
        prstate.setInt(1, group.getId());

        ResultSet result = prstate.executeQuery();

        while (result.next()) {
            System.out.println(result.getString(2));
        }
    }

    public void selectTeacher(Group group) throws SQLException {
        PreparedStatement prstate = SuperTable.con.prepareStatement(selectTeachers);
        prstate.setInt(1, group.getId());

        ResultSet result = prstate.executeQuery();

        while(result.next()) {
            System.out.println(result.getString(2));
        }
    }

    public void insertTeacher(Group group, int idTeacher) throws SQLException {
        PreparedStatement prstate = SuperTable.con.prepareStatement(insertTeacher);

        prstate.setInt(1, group.getId());
        prstate.setInt(2, idTeacher);

        prstate.executeUpdate();
    }

    public void updateTeacher(Group group, int oldTeacherId, int newTeacherId)
            throws SQLException {
        PreparedStatement prstate = SuperTable.con.prepareStatement(updateTeacher);

        prstate.setInt(1, newTeacherId);
        prstate.setInt(2, group.getId());
        prstate.setInt(3, oldTeacherId);

        prstate.executeUpdate();
    }

    public void deleteTeacher(Group group, int idTeacher) throws SQLException {
        PreparedStatement prstate = SuperTable.con.prepareStatement(deleteTeacher);

        prstate.setInt(1, group.getId());
        prstate.setInt(2, idTeacher);

        prstate.executeUpdate();
    }

    Group recordResult(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        int number = result.getInt(2);

        Group group = new Group(id, number);
        return group;
    }
}

