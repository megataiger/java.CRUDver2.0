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
    private StudentBase in = new StudentBase();



    Student2() {}

    Student2(int idStudent, String nameStudent, LocalDate birthday, Male male,
             int groupId) {
        id = idStudent;
        name = nameStudent;
        date = birthday;
        gender = male;
        group = groupId;
    }

    Student2(String nameStudent, LocalDate birthday, Male male, int groupId) {
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

    public void setGenderStudent(Male newGender) throws SQLException {
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



    String getName() {
        return name;
    }

    String getDate() {
        return date.getYear() + "-" + date.getMonthValue() +
                "-" + date.getDayOfMonth();
    }

    String getGender() {
        return gender.getValue();
    }

    int getGroupId() {
        return group;
    }

    int getId() {
        return id;
    }
}

class StudentBase extends SuperTable {

    private String select = "SELECT * FROM student";
    private String insert = "INSERT INTO student (id, name, birthday, male," +
            " group_id) VALUES (null, ?, ?, ?, ?)";
    private String update = "UPDATE student SET name = ?, birthday = ?, " +
            "male = ?, group_id = ? WHERE id = ?";
    private String delete = "DELETE FROM student WHERE id = ?";


    Student2 selectStudent(int idStudent) throws SQLException {
        select += " WHERE id = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setInt(1, idStudent);

        ResultSet result = prstate.executeQuery();

        result.next();
        Student2 student = recordResult(result);
        prstate.close();
        return student;
    }

    void selectStudent() throws SQLException {
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(select);

        while(result.next()) {
            System.out.println(recordResult(result));
        }
        state.close();
    }

    void selectStudent(String nameStudent) throws SQLException {
        select += " WHERE name = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setString(1, nameStudent);

        ResultSet result = prstate.executeQuery();

        while(result.next()) {
            System.out.println(recordResult(result));
        }
        prstate.close();
    }

    void selectStudent(LocalDate birthday) throws SQLException {
        select += " WHERE birthday = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        String value = birthday.getYear() + "-" + birthday.getMonthValue() +
                "-" + birthday.getDayOfMonth();
        prstate.setString(1, value);

        ResultSet result = prstate.executeQuery();
        while(result.next()) {
            System.out.println(recordResult(result));
        }
        prstate.close();
    }

    void selectGroup(int idGroup) throws SQLException {
        select += " WHERE group_id = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setInt(1, idGroup);
        ResultSet result = prstate.executeQuery();
        while (result.next()) {
            System.out.println(recordResult(result));
        }
        prstate.close();
    }

    void insert(Student2 student) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(insert);
        prstate.setString(1, student.getName());
        prstate.setString(2, student.getDate());
        prstate.setString(3, student.getGender());
        prstate.setInt(4, student.getGroupId());

        prstate.executeUpdate();
        prstate.close();
        }

    void update(Student2 student) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(update);
        prstate.setString(1, student.getName());
        prstate.setString(2, student.getDate());
        prstate.setString(3, student.getGender());
        prstate.setInt(4, student.getGroupId());
        prstate.setInt(5, student.getId());

        prstate.executeUpdate();
        prstate.close();
    }

    void delete(Student2 student) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(delete);
        prstate.setInt(1, student.getId());
        prstate.executeUpdate();
        prstate.close();
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
