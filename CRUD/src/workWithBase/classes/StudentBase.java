package workWithBase.classes;

import objectForStrokeBase.Student;
import objectForStrokeBase.Gender;
import workWithBase.interfaces.StudentInterface;
import workWithBase.connectWithBase.SuperTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentBase extends SuperTable implements StudentInterface {

    private String select = "SELECT * FROM student";
    private String insert = "INSERT INTO student (id, name, birthday, male," +
            " group_id) VALUES (null, ?, ?, ?, ?)";
    private String update = "UPDATE student SET name = ?, birthday = ?, " +
            "male = ?, group_id = ? WHERE id = ?";
    private String delete = "DELETE FROM student WHERE id = ?";
    private List<Student> students = new ArrayList<>();



    public Student selectStudent(int idStudent) throws SQLException {
        select += " WHERE id = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setInt(1, idStudent);

        ResultSet result = prstate.executeQuery();

        result.next();
        Student student = recordResult(result);
        prstate.close();
        return student;
    }

    public List<Student> selectStudent() throws SQLException {
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(select);

        while(result.next()) {
            students.add(recordResult(result));
        }
        state.close();
        return students;
    }

    public List<Student> selectStudent(String nameStudent) throws SQLException {
        select += " WHERE name = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setString(1, nameStudent);

        ResultSet result = prstate.executeQuery();

        while(result.next()) {
            students.add(recordResult(result));
        }
        prstate.close();
        return students;
    }

    public List<Student> selectStudent(LocalDate birthday) throws SQLException {
        select += " WHERE birthday = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        String value = birthday.getYear() + "-" + birthday.getMonthValue() +
                "-" + birthday.getDayOfMonth();
        prstate.setString(1, value);

        ResultSet result = prstate.executeQuery();
        while(result.next()) {
            students.add(recordResult(result));
        }
        prstate.close();
        return students;
    }

    public List<Student> selectGroup(int idGroup) throws SQLException {
        select += " WHERE group_id = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setInt(1, idGroup);
        ResultSet result = prstate.executeQuery();
        while (result.next()) {
            students.add(recordResult(result));
        }
        prstate.close();
        return students;
    }

    public void insert(Student student) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(insert);
        prstate.setString(1, student.getName());
        prstate.setString(2, student.getDate());
        prstate.setString(3, student.getGender());
        prstate.setInt(4, student.getGroupId());

        prstate.executeUpdate();
        prstate.close();
    }

    public void update(Student student) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(update);
        prstate.setString(1, student.getName());
        prstate.setString(2, student.getDate());
        prstate.setString(3, student.getGender());
        prstate.setInt(4, student.getGroupId());
        prstate.setInt(5, student.getId());

        prstate.executeUpdate();
        prstate.close();
    }

    public void delete(Student student) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(delete);
        prstate.setInt(1, student.getId());
        prstate.executeUpdate();
        prstate.close();
    }

    Student recordResult(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String name = result.getString(2);
        LocalDate birthday = LocalDate.parse(result.getString(3));
        Gender gender;
        if (result.getString(4).equals(Gender.MAN.getValue())) {
            gender = Gender.MAN;
        } else {
            gender = Gender.WOMAN;
        }
        int group_id = result.getInt(5);
        Student student = new Student(id, name, birthday, gender, group_id);
        return student;
    }
}