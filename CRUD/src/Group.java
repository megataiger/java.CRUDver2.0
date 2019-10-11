import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Group extends SuperTable {
    Group() {
    }
    Group(int i) {
        number = i;
    }

    public void add() throws SQLException {
        Statement state = con.createStatement();
        String query = "INSERT INTO `group` " +
                "(id, number) VALUES (null, '" + number + "')";
        state.executeUpdate(query);
        this.getId(number);
    }

    public void set(int temp) throws SQLException {
        number = temp;
        this.update();
    }

    public void get(int temp) throws SQLException {
        getId(temp);
        if (id > 0)
            number = temp;
        else
            getId(number);
    }

    void getId(int number) throws SQLException {
        Statement state = con.createStatement();
        String select = "SELECT * FROM `group` WHERE number = " + number;
        ResultSet idResult = state.executeQuery(select);
        if (idResult.next())
            id = idResult.getInt(1);
    }

    public int getId() {
        return id;
    }

    void update() throws SQLException {
        Statement state = con.createStatement();
        String update = "UPDATE `group` SET number = '" + this.number + "' WHERE id = " + id;
        state.executeUpdate(update);
    }

    void delete() throws SQLException {
        Statement state = con.createStatement();
        String delete = "DELETE FROM `group` WHERE id = " + id;
        state.executeUpdate(delete);
    }
    void info(){
        System.out.println(this.number);
    }

    public void teacherList() throws SQLException {
        Statement state = con.createStatement();
        String query = "SELECT id, name " +
                "FROM `group/teacher` JOIN teacher " +
                "ON `group/teacher`.teacher_id = teacher.id " +
                "WHERE group_id = " + id;
        ResultSet result = state.executeQuery(query);
        Teacher teach = new Teacher();
        while(result.next()) {
            teach.get(result.getString(2));
            teachers.add(teach);
        }
        for(Teacher e : teachers)
            e.info();
    }

    public boolean equals(Group group) {
        if (number == group.number)
            return true;
        else
            return false;
    }

    public void addTeacher(Teacher teacher) throws SQLException {
        Statement state = con.createStatement();
        String insert = "INSERT INTO `group/teacher` (group_id, teacher_id) VALUES " +
                "(" + id + ", " + teacher.getId() + ")";
        state.executeUpdate(insert);
        teachers.add(teacher);
    }

    public void deleteTeacher(Teacher teacher) throws SQLException {
        Statement state = con.createStatement();
        String delete = "DELETE FROM `group/teacher` WHERE group_id = " + id
                + " AND teacher_id = " + teacher.getId();
        state.executeUpdate(delete);
        for (int i = 0 ; i < teachers.size() ; i++ )
            if (teachers.get(i) == teacher)
                teachers.remove(i);
    }

    public void setTeacher (Teacher oldTeacher, Teacher newTeacher) throws SQLException {
        Statement state = con.createStatement();
        String update = "UPDATE `group/teacher` SET teacher_id = " + newTeacher.getId()
                + " WHERE group_id = " + id + " AND teacher_id = " + oldTeacher.getId();
        state.executeUpdate(update);
        for (int i = 0 ; i < teachers.size() ; i++)
            if (teachers.get(i) == oldTeacher)
                teachers.set(i, newTeacher);
    }

    public void studentList () throws SQLException, IOException{
        Statement state = con.createStatement();
        String query = "SELECT * FROM student WHERE group_id = " + id;
        ResultSet result = state.executeQuery(query);
        Student stud = new Student();
        while(result.next()) {
            stud.get(result.getString(2));
            students.add(stud);
        }
        for (Student e : students)
            e.info();
    }

    private int number;
    private int id;
    private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    private ArrayList<Student> students = new ArrayList<Student>();
}
