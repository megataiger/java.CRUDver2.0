import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.*;

/**
 * Класс Teacher предназначен для орагнизации интерфейса
 * взаимодействия с таблицей преподавателей в базе данных.
 */
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
    Teacher(String nameSname, int day, int month, int yaer, String c)
            throws SQLException {
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
            id = result.getInt(1);
            name = result.getString(2);
            birthday = LocalDate.parse(result.getString(3));
            male = result.getString(4);
        }

        String query = "SELECT number FROM `group` JOIN `group/teacher` " +
                "ON `group`.id = `group/teacher`.group_id " +
                "WHERE `group/teacher`.teacher_id = " + id;
        result = state.executeQuery(query);

        while (result.next()) {
            groups.add(new Group(result.getInt("number")));
        }
    }

    /**
     * Метод info() выводит в консоль
     * значения полей экземпляра объекта
     */
    public void info() {
        System.out.println(name + "\t" + birthday.getYear() + "-"
                + birthday.getMonthValue() + "-" + birthday.getDayOfMonth()
                + "\t" + male);
    }

    /**
     * Метод groupList() выводит список групп,
     * за которыми закреплён данный преподаватель.
     * Список выводится в соответствии с реализацией
     * метода info() класса Group.
     */
    public void groupList() {
        info();
        for (Group e : groups)
            e.info();
    }

    /**
     * Метод getId(String name) используется во время
     * добавления экземпляра объекта в базу.
     * Так как уникальный номер строки в базе инкрементируется,
     * то чтобы записать актуальные данные в соответствующее поле объекта
     * используется данный метод, который присваивает полю
     * id актуально значение.
     * @param name Ф.И.О. преподавателя, которое берётся из уже
     *             инициализированного объекта.
     * @throws SQLException
     */
    void getId(String name) throws SQLException {

        Statement state = con.createStatement();
        String query = "SELECT id FROM teacher WHERE name = '" + name + "'";
        ResultSet res = state.executeQuery(query);

        if (res.next())
            id = res.getInt(1);
    }

    /**
     * Метод add() добавляет инициализированный объект
     * в виде записи в базу данных.
     * @throws SQLException
     */
    public void add() throws SQLException {

        Statement state = con.createStatement();
        String insert = "INSERT INTO teacher (id, name, birthday, male) VALUES "
                + "(null, '" + this.name + "', '" + this.birthday.getYear()
                + "-" + birthday.getMonthValue() + "-" + birthday.getDayOfMonth()
                + "', '" + this.male + "')";

        state.executeUpdate(insert);
    }

    /**
     * Метод delete(Group b) удаляет запись об объекте из базы дынных,
     * а также удаляет записи связей с данным объектом.
     * @param b инициализированный экземпляр Group
     * @throws SQLException
     */
    public void delete(Group b) throws SQLException {

        Statement state = con.createStatement();
        String deleteCom = "DELETE FROM `group/teacher` WHERE teacher_id = " + id;

        state.executeUpdate(deleteCom);

        String delete = "DELETE FROM teacher WHERE id = " + id;
        state.executeUpdate(delete);
    }

    /**
     * Метод update() на основе полей экземпляра вносит измененяет
     * запись в базе данных. Служит для вызова внутри метода
     * set(String nameSname, int day, int month, int year, String c)
     * @throws SQLException
     */
    void update() throws SQLException {

        Statement state = con.createStatement();
        String update = "UPDATE teacher SET name = '" + name + "', birthday = '"
                + birthday.getYear() + "-" + birthday.getMonthValue()
                + "-" + birthday.getDayOfMonth() +
                 "', male = '" + male + "' WHERE id = " + id;

        state.executeUpdate(update);
    }

    /**
     * Метод addGroup(Group g) присваивает группу преподавателю
     * и записывает экземпляр Group в поле groups.
     * @param g экземпляр Group.
     * @throws SQLException
     */
    public void addGroup(Group g) throws SQLException {

        Statement state = con.createStatement();
        int groupId = g.getId();

        try {
            String insert = "INSERT INTO `group/teacher` " +
                    "(group_id, teacher_id) VALUES " +
                    "(" + groupId + ", " + id + ")";
            state.executeUpdate(insert);
            groups.add(g);
        } catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
    }

    /**
     * Метод deleteGroup(Group g) удаляет связь между преподавателем и группой
     * и стирает экземпляр группы из поля коллекции.
     * @param g экземпляр группы
     * @throws SQLException
     */
    public void deleteGroup(Group g) throws SQLException {

        Statement state = con.createStatement();

        int groupId = g.getId();
        try {

            String insert = "DELETE FROM `group/teacher` WHERE group_id = "
                    + groupId + " AND teacher_id = " + id;
            state.executeUpdate(insert);

            for (int i = 0 ; i < groups.size() ; i ++) {
                if (groups.get(i) == g)
                    groups.remove(i);
            }
        } catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
    }

    /**
     * Метод setGroup(Group a, Group b)
     * изменяет запись о связи в соответствии с заданными
     * экземплярами групп, удаляет из коллекции старую группу и
     * записывает новую.
     * @param a экземпляр существующей группы, которую заменяем
     * @param b экземпляр новой группы
     * @throws SQLException
     */
    public void setGroup(Group a, Group b) throws SQLException {

        Statement state = con.createStatement();
        int newId = b.getId();
        int oldId = a.getId();

        try {

            String update = "UPDATE `group/teacher` SET group_id = "
                    + newId + " WHERE teacher_id = "
                    + id + " AND group_id = " + oldId;
            state.executeUpdate(update);

            for(int i = 0 ; i < groups.size(); i++){
                if (groups.get(i) == a)
                    groups.remove(i);
            }
            groups.add(b);

        } catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
    }

    /**
     * Метод set(String nameSname, int day, int month, int year, String c)
     * изменяет поля инициализированного объекта и затем вызывает
     * метод update() для дальнейшего внесения изменений в базу.
     * @param nameSname Ф.И.О.
     * @param day день рождения
     * @param month месяц рождения
     * @param year год рождения
     * @param c пол М/Ж
     * @throws SQLException
     */
    public void set(String nameSname, int day, int month, int year, String c)
            throws SQLException {

        name = nameSname;
        birthday = LocalDate.of(year,month,day);
        male = c;

        update();
    }

    /**
     * Метод select() выводит в консоль весь список перподавателей
     * без присваивания конкретных значений полям.
     * @throws SQLException
     */
    public void select() throws SQLException {

        Statement state = con.createStatement();
        String select = "SELECT * FROM teacher";

        ResultSet result = state.executeQuery(select);
        ResultSetMetaData rm = result.getMetaData();

        while (result.next()) {
            for (int i = 1 ; i <= rm.getColumnCount(); i++) {
                System.out.print(result.getString(i) + "\t");
            }
            System.out.print("\n");
        }
    }

    /**
     * Метод getId() возвращает уникальный номер в базе данных.
     * Предназначен для использования в других классах, где это поле
     * может понадобиться.
     * @return целое число
     */
    public int getId() {
        return id;
    }

    /**
     * Метод equals(Teacher teacher) сравнивает
     * два объекта
     * @param teacher объект, с которым сравниваем
     * @return истина или ложь, в зависимости от того
     * равны или не равны объекты
     */
    public boolean equals(Teacher teacher) {
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
