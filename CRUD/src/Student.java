import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

class Student {
    Student (){
    }

    Student(String nameSname, Date bDate, char mOrY, int numberGroup) throws IOException, SQLException {
        name = nameSname;
        birthday = bDate;
        male = mOrY;
        group = numberGroup;

        Connection con = new Connect().getConnect();
        Statement state = con.createStatement();
        String query =  "INSERT INTO student " +
                "(id, name, birthday, male, group_id) VALUES (null, '" + name + "', '"
                + birthday.getYear() + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate() + "', '" +
                male + "', '" + 1 + "')";
        state.executeUpdate(query);
        con.close();
    }

    void update(String newName, Date newBirthday, char newMale, int newGroup){
        name = newName;
        birthday = newBirthday;
        male = newMale;
        group = newGroup;
    }

    String info(){
        String info = name + "\t" + birthday.getDate() + "/" + birthday.getMonth() + "/" + birthday.getYear() + "\t" +
                "" + male + "\t" + group;
        return info;
    }
    String getName(){
        return name;
    }

    String name;
    Date birthday;
    char male;
    int group;
}

class Students {
    public void add(String name, Date date, char c, int i)  throws IOException, SQLException{
        Student e = new Student(name, date, c, i);
        students.add(e);
    }
    public void get() {
        Student e = students.get(0);
        System.out.println(e.info());
    }
    public void set(String name, Date date, char c, int i)  throws IOException, SQLException{
        Student e = new Student(name, date, c, i);
        int l = findStudent(name);
        students.set(l, e);
    }
    private int findStudent(String name){
        Student stud = new Student();
        for (Student e : students){
            if (e.getName().equals(name)) {
                stud = e;
                break;
            }
        }
        return students.indexOf(stud);
    }

    ArrayList<Student> students = new ArrayList<Student>();
}
