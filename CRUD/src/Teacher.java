import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.*;

public class Teacher extends SuperTable {
    /**
    * Конструктор Teacher()
    * служит для создания пустого объекта
    * и последующей его инициализацией методом get()
     */
    Teacher() {
    }

    /**
     * Конструктор с параметрами
     * для инициализации объекта и последующей вставки
     * его содержимого в виде записи в базе данных
     * посредством метода add()
     * @param nameSname Ф.И.О. преподавателя
     * @param day день рождения
     * @param month месяц рождения
     * @param yaer год рождения
     * @param c пол М/Ж
     * @throws SQLException
     */
    Teacher(String nameSname, int day, int month, int yaer, String c) throws SQLException {
        name = nameSname;
        birthday = LocalDate.of(yaer, month, day);
        male = c;
        getId(name);
    }

    /**
     * Метод get(String temp) по заданому Ф.И.О.
     * ищет в базе данных соответствующего преподавателя,
     * затем инициализирует экземпляр объекта
     * @param temp Ф.И.О., по кторому будет вестись поиск
     * @throws SQLException
     */
    public void get(String temp) throws SQLException {
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
    }

    /**
     *
     */
    public void info() {
        System.out.println(this.name + "\t" + this.birthday.getYear() + "-" + this.birthday.getMonthValue()
                + "-" + this.birthday.getDayOfMonth() + "\t" + this.male);
    }

    public void groupList() {
        this.info();
        for (Group e : groups)
            e.info();
    }

    void getId(String name) throws SQLException {
        Statement state = con.createStatement();
        String query = "SELECT id FROM teacher WHERE name = '" + name + "'";
        ResultSet res = state.executeQuery(query);
        if (res.next())
            id = res.getInt(1);
    }

    public void add() throws SQLException {
        Statement state = con.createStatement();
        String insert = "INSERT INTO teacher (id, name, birthday, male) VALUES " +
                "(null, '" + this.name + "', '" + this.birthday.getYear()
                + "-" + birthday.getMonthValue() + "-" + birthday.getDayOfMonth() + "', '" + this.male + "')";
            state.executeUpdate(insert);
    }

    public void delete(Group b) throws SQLException {
        Statement state = con.createStatement();
        String deleteCom = "DELETE FROM `group/teacher` WHERE teacher_id = " + this.id;
        state.executeUpdate(deleteCom);
        String delete = "DELETE FROM teacher WHERE id = " + this.id;
        state.executeUpdate(delete);
    }

    void update() throws SQLException {
        Statement state = con.createStatement();
        String update = "UPDATE teacher SET name = '" + this.name + "', birthday = '"
                + this.birthday.getYear() + "-" + this.birthday.getMonthValue() + "-" + this.birthday.getDayOfMonth() +
                 "', male = '" + this.male + "' WHERE id = " + this.id;
        state.executeUpdate(update);
    }

    public void addGroup(Group g) throws SQLException {
        Statement state = con.createStatement();
        int groupId = g.getId();
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

    public void deleteGroup(Group g) throws SQLException {
        Statement state = con.createStatement();
        int groupId = g.getId();
        try {
            String insert = "DELETE FROM `group/teacher` WHERE group_id = " + groupId + " AND teacher_id = " + id;
            state.executeUpdate(insert);
            for (int i = 0 ; i < groups.size() ; i ++)
            {
                if (groups.get(i) == g)
                    groups.remove(i);
            }
        }
        catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
    }

    public void setGroup(Group a, Group b) throws SQLException {
        Statement state = con.createStatement();
        int newId = b.getId();
        int oldId = a.getId();
        try {
            String update = "UPDATE `group/teacher` SET group_id = " + newId + " WHERE teacher_id = "
                    + id + " AND group_id = " + oldId;
            state.executeUpdate(update);
            for(int i = 0 ; i < groups.size(); i++){
                if (groups.get(i) == a)
                    groups.remove(i);
            }
            groups.add(b);
        }
        catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
    }

    public void set(String nameSname, int day, int month, int year, String c) throws SQLException {
        this.name = nameSname;
        this.birthday = LocalDate.of(year,month,day);
        this.male = c;
        this.update();
    }

    public void select() throws SQLException {
        Statement state = con.createStatement();
        String select = "SELECT * FROM teacher";
        ResultSet result = state.executeQuery(select);
        ResultSetMetaData rm = result.getMetaData();
        while (result.next())
        {
            for (int i = 1 ; i <= rm.getColumnCount(); i++)
                System.out.print(result.getString(i) + "\t");
            System.out.print("\n");
        }
    }

    public int getId() {
        return id;
    }

    public boolean equals( Teacher teacher) {
        if ((name == teacher.name) && (birthday == teacher.birthday))
            return true;
        else
            return false;
    }

    private int id;
    private String name;
    private LocalDate birthday;
    private String male;
    private ArrayList<Group> groups = new ArrayList<Group>();
}
