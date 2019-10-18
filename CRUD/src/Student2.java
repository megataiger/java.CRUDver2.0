import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Student2 {
    private int id;
    private String name;
    private LocalDate date;
    private Male gender;
    private int group;

    Student2() {}

    Student2(int idStudent, String nameStudent, LocalDate birthday, Male male, int groupId) {
        id = idStudent;
        name = nameStudent;
        date = birthday;
        gender = male;
        group = groupId;
    }

    public String toString() {
        return id + "\t" + name + "\t" + date + "\t" + gender.getValue() +
                "\t" + group;
    }

    public void get() throws SQLException {
        StudentBase in = new StudentBase();
        in.selectStudent();
    }

    public void get(int idStudent) throws SQLException {
        StudentBase in = new StudentBase();
        System.out.println(in.selectStudent(idStudent));
    }

    public void get(String nameStudent) throws SQLException {
        StudentBase in = new StudentBase();
        in.selectStudent(nameStudent);
    }

    public static void main (String[] args) throws SQLException {
        Student2 student = new Student2();
        student.get("Магомедов Илья Магомедович");
    }

}

class StudentBase extends SuperTable {
    private String select = "SELECT * FROM student";
    private String insert = "INSERT INTO student (id, name, birthday, male, group_id) " +
            "VALUES (null, ?, ?, ?, ?)";
    private String update = "UPDATE student SET name = ?, birhday = ?, " +
            "male = ?, group_id = ? WHERE id = ?";
    private String delete = "DELETE FROM student WHERE id = ?";

    Student2 selectStudent(int idStudent) throws SQLException {
        select += " WHERE id = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setInt(1, idStudent);

        ResultSet result = prstate.executeQuery();

        result.next();
        Student2 student = recordResult(result);
        return student;
    }

    void selectStudent() throws SQLException {
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(select);

        while(result.next()) {
            System.out.println(recordResult(result));
        }
    }

    void selectStudent(String nameStudent) throws SQLException {
        select += " WHERE name = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setString(1, nameStudent);

        ResultSet result = prstate.executeQuery();
        while(result.next()) {
            System.out.println(recordResult(result));
        }
    }

    Student2 recordResult(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String name = result.getString(2);
        LocalDate birthday = LocalDate.parse(result.getString(3));
        Male male;
        if (result.getString(4).equals(Male.MAN.getValue())) {
            male = Male.MAN;
        } else {
            male = Male.WOMAN;
        }
        int group_id = result.getInt(5);
        Student2 student = new Student2(id, name, birthday, male, group_id);
        return student;
    }
}
