import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Group2 {
    private int id;
    private int number;
    private GroupBase in = new GroupBase();



    Group2() {
    }

    Group2(int idGroup, int numberGroup) {
        id = idGroup;
        number = numberGroup;
    }

    Group2(int numberGroup) {
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



    int getId() {
        return id;
    }

    int getNumber() {
        return number;
    }
}

class GroupBase extends SuperTable {
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


    Group2 selectGroupById(int idGroup) throws SQLException {
        select += " WHERE id = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setInt(1, idGroup);

        ResultSet result = prstate.executeQuery();

        result.next();
        Group2 group = recordResult(result);
        return group;
    }

    Group2 selectGroupByNumber(int numberGroup) throws SQLException {
        select += " WHERE number = ?";
        PreparedStatement prstate = con.prepareStatement(select);
        prstate.setInt(1, numberGroup);

        ResultSet result = prstate.executeQuery();

        result.next();
        Group2 group = recordResult(result);
        return group;
    }

    void select() throws SQLException {
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery(select);
        while (result.next()) {
            System.out.println(recordResult(result));
        }
    }

    void insert(Group2 group) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(insert);
        prstate.setInt(1, group.getNumber());
        if(selectGroupByNumber(group.getNumber()).equals(group)) {
            System.out.println("Группа с таким номером уже существует");
        } else {
            prstate.executeUpdate();
        }
    }

    void update(Group2 group) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(update);
        prstate.setInt(1, group.getNumber());
        prstate.setInt(2, group.getId());

        prstate.executeUpdate();
    }

    void delete(Group2 group) throws SQLException {
        String deleteRel = "DELETE FROM `group_teacher` WHERE group_id = ?";
        PreparedStatement prstate = con.prepareStatement(deleteRel);

        prstate.setInt(1, group.getId());
        prstate.executeUpdate();
        prstate = con.prepareStatement(delete);
        prstate.setInt(1, group.getId());

        prstate.executeUpdate();
    }

    void selectStudent(Group2 group) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(selectStudent);
        prstate.setInt(1, group.getId());

        ResultSet result = prstate.executeQuery();

        while (result.next()) {
            System.out.println(result.getString(2));
        }
    }

    void selectTeacher(Group2 group) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(selectTeachers);
        prstate.setInt(1, group.getId());

        ResultSet result = prstate.executeQuery();

        while(result.next()) {
            System.out.println(result.getString(2));
        }
    }

    void insertTeacher(Group2 group, int idTeacher) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(insertTeacher);

        prstate.setInt(1, group.getId());
        prstate.setInt(2, idTeacher);

        prstate.executeUpdate();
    }

    void updateTeacher(Group2 group, int oldTeacherId, int newTeacherId)
            throws SQLException {
        PreparedStatement prstate = con.prepareStatement(updateTeacher);

        prstate.setInt(1, newTeacherId);
        prstate.setInt(2, group.getId());
        prstate.setInt(3, oldTeacherId);

        prstate.executeUpdate();
    }

    void deleteTeacher(Group2 group, int idTeacher) throws SQLException {
        PreparedStatement prstate = con.prepareStatement(deleteTeacher);

        prstate.setInt(1, group.getId());
        prstate.setInt(2, idTeacher);

        prstate.executeUpdate();
    }

    Group2 recordResult(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        int number = result.getInt(2);

        Group2 group = new Group2(id, number);
        return group;
    }
}
