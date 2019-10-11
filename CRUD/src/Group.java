import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Group {
    Group() {
    }
    Group(int i) {
        number = i;
    }

    public void add() throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String query = "INSERT INTO `group` " +
                "(id, number) VALUES (null, '" + number + "')";
        state.executeUpdate(query);
        con.close();
        this.getId(number);
    }

    public void set(int temp) throws SQLException, IOException {
        number = temp;
        this.update();
    }

    public void get(int temp) throws IOException, SQLException {
        this.getId(temp);
        if (this.id > 0)
            this.number = temp;
        else
            this.getId(number);
        //getLists();
    }

    void getId(int number) throws IOException, SQLException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String select = "SELECT * FROM `group` WHERE number = " + number;
        ResultSet idResult = state.executeQuery(select);
        if (idResult.next())
            id = idResult.getInt(1);
        con.close();
    }

    public int getId() {
        return id;
    }

    void update() throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String update = "UPDATE `group` SET number = '" + this.number + "' WHERE id = " + id;
        state.executeUpdate(update);
        con.close();
    }

    void delete() throws IOException, SQLException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String delete = "DELETE FROM `group` WHERE id = " + id;
        state.executeUpdate(delete);
        con.close();
    }
    void info(){
        System.out.println(this.number);
    }

    public void teacherList() throws IOException, SQLException {
        for(Teacher e : teachers)
            e.info();
    }

    public boolean equals(Group group) {
        if (number == group.number)
            return true;
        else
            return false;
    }

    public void addTeacher(Teacher teacher) throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String insert = "INSERT INTO `group/teacher` (group_id, teacher_id) VALUES " +
                "(" + id + ", " + teacher.getId() + ")";
        state.executeUpdate(insert);
        con.close();
        teachers.add(teacher);
    }

    public void deleteTeacher(Teacher teacher) throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String delete = "DELETE FROM `group/teacher` WHERE group_id = " + id
                + " AND teacher_id = " + teacher.getId();
        state.executeUpdate(delete);
        con.close();
        for (int i = 0 ; i < teachers.size() ; i++ )
            if (teachers.get(i) == teacher)
                teachers.remove(i);
    }

    public void setTeacher (Teacher oldTeacher, Teacher newTeacher) throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String update = "UPDATE `group/teacher` SET teacher_id = " + newTeacher.getId()
                + " WHERE group_id = " + id + " AND teacher_id = " + oldTeacher.getId();
        state.executeUpdate(update);
        con.close();
        for (int i = 0 ; i < teachers.size() ; i++)
            if (teachers.get(i) == oldTeacher)
                teachers.set(i, newTeacher);
    }

    public void studentList () throws SQLException, IOException {
        for (Student e : students)
            e.info();
    }

    public void getLists () throws SQLException,IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String query = "SELECT * FROM student WHERE group_id = " + id;
        ResultSet result = state.executeQuery(query);
        Student stud = new Student();
        Teacher teach = new Teacher();
        while(result.next()) {
            stud.get(result.getString(2));
            students.add(stud);
        }
        query = "SELECT id, name " +
                "FROM `group/teacher` JOIN teacher " +
                "ON `group/teacher`.teacher_id = teacher.id " +
                "WHERE group_id = " + id;
        result = state.executeQuery(query);
        while(result.next()) {
            teach.get(result.getString(2));
            teachers.add(teach);
        }
        con.close();
    }

    public static void main(String[] args) throws IOException, SQLException {
        Group a = new Group();
        a.get(15);
        a.info();
        a.getLists();
        a.teacherList();
        a.studentList();
    }

    private int number;
    private int id;
    private ArrayList<Teacher> teachers;
    private ArrayList<Student> students;
}
