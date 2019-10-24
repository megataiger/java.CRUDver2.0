package workWithBase;

import objectForStrokeBase.Teacher2;
import objectForStrokeBase.Gender;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class TeacherBase extends SuperTable {
    private String select = "SELECT * FROM teacher";
    private String insert = "INSERT INTO teacher (id, name, birthday, male)" +
            "VALUES (null, ?, ?, ?)";
    private String update = "UPDATE teacher " +
            "SET name = ?, birthday = ?, male = ? WHERE id = ?";
    private String delete = "DELETE FROM teacher WHERE id = ?";
    private String selectGroups = "SELECT number " +
            "FROM `group` JOIN `group_teacher` " +
            "ON `group`.id = `group_teacher`.group_id " +
            "WHERE `group_teacher`.teacher_id = ?";
    private String insertGroup = "INSERT INTO `group_teacher` " +
            "(group_id, teacher_id) VALUES " +
            "(?, ?)";
    private String updateGroup = "UPDATE `group_teacher` SET group_id = ? " +
            "WHERE teacher_id = ? AND group_id = ?";
    private String deleteGroup = "DELETE FROM `group_teacher` " +
            "WHERE group_id ? AND teacher_id = ?";


    public Teacher2 selectTeacher(int idTeacher) throws SQLException {
        select += " WHERE id = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setInt(1, idTeacher);

        ResultSet result = prstate.executeQuery();

        result.next();
        Teacher2 teacher = recordResult(result);
        return teacher;
    }

    public void selectTeacher() throws SQLException {
        Statement state = con.createStatement();

        ResultSet result = state.executeQuery(select);

        while(result.next()) {
            System.out.println(recordResult(result));
        }
    }

    public void selectTeacher(String nameTeacher) throws SQLException {
        select += " WHERE name = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setString(1, nameTeacher);

        ResultSet result = prstate.executeQuery();

        while(result.next()) {
            System.out.println(recordResult(result));
        }
    }

    public void selectStudent(LocalDate birthday) throws SQLException {
        select += " WHERE birthday = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        String value = birthday.getYear() + "-" + birthday.getMonthValue() +
                "-" + birthday.getDayOfMonth();
        prstate.setString(1, value);

        ResultSet result = prstate.executeQuery();

        while (result.next()) {
            System.out.println(recordResult(result));
        }
    }

    public void insert(Teacher2 teacher) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(insert);

        prstate.setString(1, teacher.getName());
        prstate.setString(2, teacher.getDate());
        prstate.setString(3, teacher.getGender());

        prstate.executeUpdate();
    }

    public void update(Teacher2 teacher) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(update);

        prstate.setString(1, teacher.getName());
        prstate.setString(2, teacher.getDate());
        prstate.setString(3, teacher.getGender());
        prstate.setInt(4, teacher.getId());

        prstate.executeUpdate();
    }

    public void delete(Teacher2 teacher) throws SQLException {
        String deleteRel = "DELETE FROM `group_teacher` WHERE teacher_id = ?";
        PreparedStatement prstate = con.prepareStatement(deleteRel);

        prstate.setInt(1, teacher.getId());
        prstate.executeUpdate();

        prstate = con.prepareStatement(delete);
        prstate.setInt(1, teacher.getId());

        prstate.executeUpdate();
    }

    public void selectGroups(Teacher2 teacher) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(selectGroups);
        prstate.setInt(1, teacher.getId());

        ResultSet result = prstate.executeQuery();

        while (result.next()) {
            System.out.println(result.getString(1));
        }
    }

    public void insertGroup(Teacher2 teacher, int groupId) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(insertGroup);

        prstate.setInt(1, groupId);
        prstate.setInt(2, teacher.getId());

        prstate.executeUpdate();
    }

    public void updateGroup(Teacher2 teacher, int oldGroupId, int newGroupId)
            throws SQLException {
        PreparedStatement prstate = con.prepareStatement(updateGroup);

        prstate.setInt(1, newGroupId);
        prstate.setInt(2, teacher.getId());
        prstate.setInt(3, oldGroupId);

        prstate.executeUpdate();
    }

    public void deleteGroup(Teacher2 teacher, int groupId) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(deleteGroup);

        prstate.setInt(1, groupId);
        prstate.setInt(2, teacher.getId());

        prstate.executeUpdate();
    }

    Teacher2 recordResult(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String name = result.getString(2);
        LocalDate birthday = LocalDate.parse(result.getString(3));

        Gender gender;
        if (result.getString(4).equals(Gender.MAN.getValue())) {
            gender = Gender.MAN;
        } else {
            gender = Gender.WOMAN;
        }

        Teacher2 teacher = new Teacher2(id, name, birthday, gender);
        return teacher;
    }
}