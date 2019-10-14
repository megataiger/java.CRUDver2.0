import java.time.LocalDate;
import java.sql.*;

/**
 * Класс Student предназначен для асбтракции записей о студентах
 * и работы с ними
 */
public class Student extends SuperTable {
    /**
     * Пустой конструктор для последующей инициализации
     * существующей записи о студенте.
     */
    Student () {
    }

    /**
     * Конструктор для записи данных о студенте в экземпляр объекта.
     * @param nameSname Ф.И.О. студента
     * @param day день рождения
     * @param month месяц рождения
     * @param year год рождения
     * @param mOrY пол М/Ж
     * @param numberGroup номер группы студента
     * @throws SQLException
     */
    Student(String nameSname, int day, int month, int year,
            String mOrY, int numberGroup)
            throws SQLException {
        name = nameSname;
        birthday = LocalDate.of(year, month, day);
        male = mOrY;
        group.get(numberGroup);
    }

    /**
     * Метод присваивает полю id уникальный идентификатор
     * строки, хранящийся в базе
     * @throws SQLException
     */
    void getId () throws SQLException {
        Statement state =  con.createStatement();
        String select = "SELECT id FROM student WHERE name = '" + name + "'";
        ResultSet res = state.executeQuery(select);
        if(res.next()) {
            id = res.getInt(1);
        }
    }

    /**
     * Метод добавляет новую строку в таблице, заполняя атрибуты значениями,
     * которые хранятся в полях объекта.
     * @throws SQLException
     */
    public void add () throws SQLException {
        Statement state = con.createStatement();
        String query =  "INSERT INTO student " +
                "(id, name, birthday, male, group_id) VALUES (null, '"
                + name + "', '" + birthday.getYear() + "-"
                + birthday.getMonthValue() + "-" + birthday.getDayOfMonth()
                + "', '" + male + "', '" + group.getId() + "')";
        try {
            state.executeUpdate(query);
            getId();
        } catch (SQLException e) {
            System.out.println("Неверный номер группы.\n " +
                    "Группы с данным номером не существет.");
        }
    }

    /**
     * Изменяет данные в полях объекта, а затем посредством вызова функции
     *  update() изменяет атрибуты соответствующей строки. Если какие- то из
     *  значений не подлежат изменению, в вызов функции пишутся старые.
     * @param newName новые Ф.И.О.
     * @param day новый день рождения
     * @param month новый месяц рождения
     * @param year новый год рождения
     * @param newMale новый пол
     * @param newGroup новый номер группы
     * @throws SQLException
     */
    public void set(String newName, int day, int month, int year,
                    String newMale, int newGroup)
            throws SQLException {
        name = newName;
        birthday = LocalDate.of(year, month, day);
        male = newMale;
        group.get(newGroup);
        update();
    }

    /**
     * Метод изменяет значения в базе.
     * Значения полей передаются в строку запроса, а затем запрос посылается
     * на отработку в базу данных
     * @throws SQLException
     */
    void update() throws SQLException {
        Statement state = con.createStatement();
        String update = "UPDATE student SET name = '" + name + "', birthday = '"
                + birthday.getYear() + "-" + birthday.getMonthValue() + "-"
                + birthday.getDayOfMonth() + "', male = '" + male
                + "', group_id = " + group.getId() + " WHERE id = " + id;
        state.executeUpdate(update);
    }

    /**
     * Выводит в консоль строку информации об объекте.
     */
    public void info(){
        String info = name + "\t" + birthday.getDayOfMonth() + "/"
                + birthday.getMonthValue() + "/" + birthday.getYear()
                + "\t" + "" + male + "\t";
        System.out.print(info);
        group.info();
    }

    /**
     * Находит запись о студенте и инициализирует значения
     * атрибутов в поля объекта.
     * @param nameSname Ф.И.О.
     * @throws SQLException
     */
    public void get(String nameSname) throws SQLException {

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

        group = new Group();
        if (result.next())
            group.get(result.getInt(1));
    }

    /**
     * Удаляет запись о студенте из базы.
     * @throws SQLException
     */
    public void delete() throws SQLException {
        Statement state = con.createStatement();
        String delete  = "DELETE FROM student WHERE id = " + id;
        state.executeUpdate(delete);
    }

    /**
     * Изменяет номер группы, к которой принадлежит студент.
     * @param number номер группы
     * @throws SQLException
     */
    public void setGroup(int number) throws SQLException {
        Group a = new Group();
        a.get(number);
        group = a;
        update();
    }

    private  int id;
    private String name;
    private LocalDate birthday;
    private String male;
    private Group group = new Group();
}

