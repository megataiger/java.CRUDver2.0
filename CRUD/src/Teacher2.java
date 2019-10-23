import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Teacher2 {
    private int id;
    private String name;
    private LocalDate date;
    private Male gender;
    private TeacherBase in = new TeacherBase();


    Teacher2() {
    }

    Teacher2(int idTeacher, String nameTeacher, LocalDate birthday, Male male) {
        id = idTeacher;
        name = nameTeacher;
        date = birthday;
        gender = male;
    }

    Teacher2(String nameTeacher, LocalDate birthday, Male male) {
        name = nameTeacher;
        date = birthday;
        gender = male;
    }



    public String toString() {
        return id + "\t" + name + "\t" + date + "\t" + gender.getValue();
    }

    public void get() throws SQLException {
        in.selectTeacher();
    }

    public void get(int idTeacher) throws SQLException {
        Teacher2 teacher = in.selectTeacher(idTeacher);
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

    public void setGenderTeacher(Male newGender) throws SQLException {
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

    int getId() {
        return id;
    }



    public static void main(String[] args) throws SQLException {
        Teacher2 teach = new Teacher2();
        teach.get(8);
        teach.remove();
    }
}

class TeacherBase extends SuperTable {
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


    Teacher2 selectTeacher(int idTeacher) throws SQLException {
        select += " WHERE id = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setInt(1, idTeacher);

        ResultSet result = prstate.executeQuery();

        result.next();
        Teacher2 teacher = recordResult(result);
        return teacher;
    }

    void selectTeacher() throws SQLException {
        Statement state = con.createStatement();

        ResultSet result = state.executeQuery(select);

        while(result.next()) {
            System.out.println(recordResult(result));
        }
    }

    void selectTeacher(String nameTeacher) throws SQLException {
        select += " WHERE name = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setString(1, nameTeacher);

        ResultSet result = prstate.executeQuery();

        while(result.next()) {
            System.out.println(recordResult(result));
        }
    }

    void selectStudent(LocalDate birthday) throws SQLException {
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

    void insert(Teacher2 teacher) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(insert);

        prstate.setString(1, teacher.getName());
        prstate.setString(2, teacher.getDate());
        prstate.setString(3, teacher.getGender());

        prstate.executeUpdate();
    }

    void update(Teacher2 teacher) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(update);

        prstate.setString(1, teacher.getName());
        prstate.setString(2, teacher.getDate());
        prstate.setString(3, teacher.getGender());
        prstate.setInt(4, teacher.getId());

        prstate.executeUpdate();
    }

    void delete(Teacher2 teacher) throws SQLException {
        String deleteRel = "DELETE FROM `group_teacher` WHERE teacher_id = ?";
        PreparedStatement prstate = con.prepareStatement(deleteRel);

        prstate.setInt(1, teacher.getId());
        prstate.executeUpdate();

        prstate = con.prepareStatement(delete);
        prstate.setInt(1, teacher.getId());

        prstate.executeUpdate();
    }

    void selectGroups(Teacher2 teacher) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(selectGroups);
        prstate.setInt(1, teacher.getId());

        ResultSet result = prstate.executeQuery();

        while (result.next()) {
            System.out.println(result.getString(1));
        }
    }

    void insertGroup(Teacher2 teacher, int groupId) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(insertGroup);

        prstate.setInt(1, groupId);
        prstate.setInt(2, teacher.getId());

        prstate.executeUpdate();
    }

    void updateGroup(Teacher2 teacher, int oldGroupId, int newGroupId)
            throws SQLException {
        PreparedStatement prstate = con.prepareStatement(updateGroup);

        prstate.setInt(1, newGroupId);
        prstate.setInt(2, teacher.getId());
        prstate.setInt(3, oldGroupId);

        prstate.executeUpdate();
    }

    void deleteGroup(Teacher2 teacher, int groupId) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(deleteGroup);

        prstate.setInt(1, groupId);
        prstate.setInt(2, teacher.getId());

        prstate.executeUpdate();
    }

    Teacher2 recordResult(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String name = result.getString(2);
        LocalDate birthday = LocalDate.parse(result.getString(3));

        Male male;
        if (result.getString(4).equals(Male.MAN.getValue())) {
            male = Male.MAN;
        } else {
            male = Male.WOMAN;
        }

        Teacher2 teacher = new Teacher2(id, name, birthday, male);
        return teacher;
    }
}
