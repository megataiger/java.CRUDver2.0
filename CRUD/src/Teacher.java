import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

public class Teacher {
    Teacher() {}
    Teacher(String nameSname, int day, int month, int yaer, String c) throws SQLException, IOException {
        name = nameSname;
        birthday = LocalDate.of(yaer, month, day);
        male = c;
        getId(name);
    }

    public void get(String temp) throws IOException, SQLException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String select = "SELECT * FROM teacher WHERE name = '" + temp + "'";
        ResultSet result = state.executeQuery(select);
        if(result.next()) {
            this.id = result.getInt(1);
            this.name = result.getString(2);
            this.birthday = LocalDate.parse(result.getString(3));
            this.male = result.getString(4);
        }
        String query = "SELECT number FROM `group` JOIN `group/teacher` " +
                "ON `group`.id = `group/teacher`.group_id " +
                "WHERE `group/teacher`.teacher_id = " + this.id;
        result = state.executeQuery(query);
        while (result.next()) {
            groups.add(new Group(result.getInt("number")));
        }
        con.close();
    }

    public void info() {
        System.out.println(this.name + "\t" + this.birthday.getYear() + "-" + this.birthday.getMonthValue()
                + "-" + this.birthday.getDayOfMonth() + "\t" + this.male);
    }

    public void groupList() {
        this.info();
        for (Group e : groups)
            e.info();
    }

    void getId(String name) throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String query = "SELECT id FROM teacher WHERE name = '" + name + "'";
        ResultSet res = state.executeQuery(query);
        if (res.next())
            id = res.getInt(1);
        con.close();
    }

    public void add() throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String insert = "INSERT INTO teacher (id, name, birthday, male) VALUES " +
                "(null, '" + this.name + "', '" + this.birthday.getYear()
                + "-" + birthday.getMonthValue() + "-" + birthday.getDayOfMonth() + "', '" + this.male + "')";
        System.out.println(insert);
            state.executeUpdate(insert);
        con.close();
    }

    public void delete(Group b) throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String deleteCom = "DELETE FROM `group/teacher` WHERE teacher_id = " + this.id;
        state.executeUpdate(deleteCom);
        String delete = "DELETE FROM teacher WHERE id = " + this.id;
        state.executeUpdate(delete);
        con.close();
    }

    void update() throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String update = "UPDATE teacher SET name = '" + this.name + "', birthday = '"
                + this.birthday.getYear() + "-" + this.birthday.getMonthValue() + "-" + this.birthday.getDayOfMonth() +
                 "', male = '" + this.male + "' WHERE id = " + this.id;
        state.executeUpdate(update);
        con.close();
    }

    public void addGroup(Group g) throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        int groupId = g.getId();
        System.out.println(groupId);
        try {
            String insert = "INSERT INTO `group/teacher` (group_id, teacher_id) VALUES " +
                    "(" + groupId + ", " + id + ")";
            state.executeUpdate(insert);
            groups.add(g);
        }
        catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
    }

    public void deleteGroup(Group g) throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        int groupId = g.getId();
        try {
            String insert = "DELETE FROM `group/teacher` WHERE group_id = " + groupId + " AND teacher_id = " + id;
            System.out.println(insert);
            state.executeUpdate(insert);
            groups.remove(g);
        }
        catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
        finally {
            con.close();
        }
    }

    public void setGroup(Group a, Group b) throws SQLException, IOException {
        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        int newId = b.getId();
        int oldId = a.getId();
        try {
            String update = "UPDATE `group/teacher` SET group_id = " + newId + " WHERE teacher_id = "
                    + id + " AND group_id = " + oldId;
            System.out.println(update);
            state.executeUpdate(update);
            for(Group e : groups){
                if (e == a)
                    groups.remove(e);
            }
            groups.add(b);
        }
        catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
        finally {
            con.close();
        }
    }

    public void set(String nameSname, int day, int month, int year, String c) throws IOException, SQLException {
        this.name = nameSname;
        this.birthday = LocalDate.of(year,month,day);
        this.male = c;
        this.update();
    }

    public static void main(String[] args) throws IOException, SQLException {
    }

    private int id;
    private String name;
    private LocalDate birthday;
    private String male;
    private ArrayList<Group> groups = new ArrayList<Group>();
}
