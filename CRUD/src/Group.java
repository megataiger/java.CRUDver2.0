import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void updateGroup(int temp) throws SQLException, IOException {
        number = temp;
        this.update();
    }

    public void get(int temp) throws IOException, SQLException {
        this.getId(temp);
        if (this.id > 0)
            this.number = temp;
        else
            this.getId(number);
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
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String select = "SELECT id, name " +
                "FROM `group/teacher` JOIN teacher " +
                "ON `group/teacher`.teacher_id = teacher.id " +
                "WHERE group_id = " + this.id;
        ResultSet teacherResult = state.executeQuery(select);
        while(teacherResult.next())
        {
            System.out.println(teacherResult.getString(2));
        }
        con.close();
    }

    public boolean equals(Group group) {
        if (number == group.number)
            return true;
        else
            return false;
    }



    public static void main(String[] args) throws IOException, SQLException {
        Group one = new Group();
        one.get(10);
        one.teacherList();
    }

    private int number;
    private int id;
    private ArrayList<Teacher> teachers;
    private ArrayList<Student> students;
}
