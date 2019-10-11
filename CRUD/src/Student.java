import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

public class Student {
    Student (Connection mainCon){
        con = mainCon;
    }

    Student(String nameSname, int day, int month, int year, String mOrY, int numberGroup, Connection mainCon) throws IOException, SQLException {
        con = mainCon;
        name = nameSname;
        birthday = LocalDate.of(year, month, day);
        male = mOrY;
        group.get(numberGroup);
    }
    
    void getId () throws SQLException {
        Statement state =  con.createStatement();
        String select = "SELECT id FROM student WHERE name = '" + name + "'";
        ResultSet res = state.executeQuery(select);
        if(res.next())
            id = res.getInt(1);
    }

    public void add () throws SQLException {
        Statement state = con.createStatement();
        String query =  "INSERT INTO student " +
                "(id, name, birthday, male, group_id) VALUES (null, '" + name + "', '"
                + birthday.getYear() + "-" + birthday.getMonthValue() + "-" + birthday.getDayOfMonth() + "', '" +
                male + "', '" + group.getId() + "')";
        try {
            state.executeUpdate(query);
            getId();
        } catch (SQLException e) {
            System.out.println("Неверный номер группы.\n Группы с данным номером не существет.");
        }
    }
    
    public void set(String newName, int day, int month, int year, String newMale, int newGroup) throws IOException, SQLException {
        name = newName;
        birthday = LocalDate.of(year, month, day);
        male = newMale;
        group.get(newGroup);
        this.update();
    }
    
    void update() throws SQLException {
        Statement state = con.createStatement();
        String update = "UPDATE student SET name = '" + name + "', birthday = '" +
                birthday.getYear() + "-" + birthday.getMonthValue() + "-" + birthday.getDayOfMonth() +
                "', male = '" + male + "', group_id = " + group.getId() + " WHERE id = " + id;
        state.executeUpdate(update);
    }

    public void info(){
        String info = name + "\t" + birthday.getDayOfMonth() + "/" + birthday.getMonthValue() + "/" + birthday.getYear() + "\t" +
                "" + male + "\t";
        System.out.print(info);
        group.info();
    }
    
    public void get(String nameSname) throws SQLException, IOException {
        int groupId = 0;
        Statement state = con.createStatement();
        String select = "SELECT * FROM student WHERE name = '" + nameSname + "'";
        ResultSet result = state.executeQuery(select);
        if(result.next()) {
            id = result.getInt(1);
            name = result.getString(2);
            birthday = LocalDate.parse(result.getString(3));
            male = result.getString(4);
            groupId = result.getInt(5);
        }
        String selectGroup = "SELECT number FROM `group` WHERE id = " + groupId;
        result = state.executeQuery(selectGroup);
        group = new Group(con);
        if (result.next())
            group.get(result.getInt(1));
    }
    
    public void delete() throws SQLException {
        Statement state = con.createStatement();
        String delete  = "DELETE FROM student WHERE id = " + id;
        state.executeUpdate(delete);
    }

    public void setGroup(int number) throws SQLException, IOException {
        Group a = new Group(con);
        a.get(number);
        group = a;
        update();
    }

    private  int id;
    private String name;
    private LocalDate birthday;
    private String male;
    private Connection con;
    private Group group;
}

