import javax.crypto.NullCipher;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.*;

/**
 * Класс Teacher предназначен для орагнизации интерфейса
 * взаимодействия с таблицей преподавателей в базе данных.
 */
public class Teacher extends SuperTable {

    private int id;
    private String name;
    private LocalDate birthday;
    private Male male;
    private ArrayList<Group> groups = new ArrayList<Group>();

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
     * В случае, если checkAvailible() возвращает истину
     * поля объекта зануляются
     * @param nameSname Ф.И.О. преподавателя
     * @param day день рождения
     * @param month месяц рождения
     * @param year год рождения
     * @param value пол М/Ж
     * @throws SQLException
     */
    Teacher(String nameSname, int day, int month, int year, Male value)
            throws SQLException {
        name = nameSname;
        birthday = LocalDate.of(year, month, day);
        male = value;
        if (this.chekAvailible()) {
            name = "";
            birthday = null;
            male = null;
            System.out.println("Преподаватель с такими данными уже существует");
        }
    }

    /**
     * Метод get(String nameExistingTeacher) по заданому Ф.И.О.
     * ищет в базе данных соответствующего преподавателя,
     * затем инициализирует экземпляр объекта
     * @param nameExistingTeacher Ф.И.О., по кторому будет вестись поиск
     * @throws SQLException
     */
    public void get(String nameExistingTeacher) throws SQLException {

        Statement state = con.createStatement();
        String select = "SELECT * FROM teacher WHERE name = '"
                + nameExistingTeacher + "'";
        ResultSet result = state.executeQuery(select);
        result.last();
        if(result.getRow() > 1) {
            System.out.println("Под данным именем существует " +
                    "несколько преподавателей." +
                    "\nВозпользуйтесь вводом дополнительного критерия.");
        } else {
            result.beforeFirst();
            if (result.next()) {
                id = result.getInt(1);
                name = result.getString(2);
                birthday = LocalDate.parse(result.getString(3));
                if (result.getString(4) == Male.MAN.getValue()) {
                    male = Male.MAN;
                } else {
                    male = Male.WOMAN;
                }
            }

            String query = "SELECT number FROM `group` JOIN `group_teacher` " +
                    "ON `group`.id = `group_teacher`.group_id " +
                    "WHERE `group_teacher`.teacher_id = " + id;
            result = state.executeQuery(query);

            while (result.next()) {
                Group group = new Group();
                group.get(result.getInt("number"));
                groups.add(group);
            }
        }
    }

    /**
     * Метод info() выводит в консоль
     * значения полей экземпляра объекта
     */
    public String toString() {
        return name + "\t" + birthday.getYear() + "-"
                + birthday.getMonthValue() + "-" + birthday.getDayOfMonth()
                + "\t" + male.getValue();
    }

    /**
     * Метод groupList() выводит список групп,
     * за которыми закреплён данный преподаватель.
     * Список выводится в соответствии с реализацией
     * метода info() класса Group.
     */
    public void groupList() {
        System.out.println(this);
        for (Group e : groups)
            System.out.println(e);
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
                + "(null, '" + name + "', '" + birthday.getYear()
                + "-" + birthday.getMonthValue() + "-" + birthday.getDayOfMonth()
                + "', '" + male.getValue() + "')";

        state.executeUpdate(insert);
        getId(name);
    }

    /**
     * Метод delete() удаляет запись об объекте из базы дынных,
     * а также удаляет записи связей с данным объектом.
     * @throws SQLException
     */
    public void delete() throws SQLException {

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
                 "', male = '" + male.getValue() + "' WHERE id = " + id;

        state.executeUpdate(update);
    }

    /**
     * Метод addGroup(Group newGroup) присваивает группу преподавателю
     * и записывает экземпляр Group в поле groups.
     * @param newGroup экземпляр Group.
     * @throws SQLException
     */
    public void addGroup(Group newGroup) throws SQLException {

        Statement state = con.createStatement();
        int groupId = newGroup.getId();

        try {
            String insert = "INSERT INTO `group_teacher` " +
                    "(group_id, teacher_id) VALUES " +
                    "(" + groupId + ", " + id + ")";
            state.executeUpdate(insert);
            groups.add(newGroup);
        } catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
    }

    /**
     * Метод deleteGroup(Group existingGroup) удаляет связь между преподавателем и группой
     * и стирает экземпляр группы из поля коллекции.
     * @param existingGroup экземпляр группы
     * @throws SQLException
     */
    public void deleteGroup(Group existingGroup) throws SQLException {

        Statement state = con.createStatement();

        int groupId = existingGroup.getId();
        try {

            String insert = "DELETE FROM `group_teacher` WHERE group_id = "
                    + groupId + " AND teacher_id = " + id;
            state.executeUpdate(insert);

            for (int i = 0 ; i < groups.size() ; i ++) {
                if (groups.get(i) == existingGroup)
                    groups.remove(i);
            }
        } catch (SQLException e){
            System.out.println("Неверный номер группы");
        }
    }

    /**
     * Метод setGroup(Group oldGroup, Group newGroup)
     * изменяет запись о связи в соответствии с заданными
     * экземплярами групп, удаляет из коллекции старую группу и
     * записывает новую.
     * @param oldGroup экземпляр существующей группы, которую заменяем
     * @param newGroup экземпляр новой группы
     * @throws SQLException
     */
    public void setGroup(Group oldGroup, Group newGroup) throws SQLException {

        Statement state = con.createStatement();
        int newId = newGroup.getId();
        int oldId = oldGroup.getId();

        try {

            String update = "UPDATE `group_teacher` SET group_id = "
                    + newId + " WHERE teacher_id = "
                    + id + " AND group_id = " + oldId;
            state.executeUpdate(update);

            for(int i = 0 ; i < groups.size(); i++){
                if (groups.get(i) == oldGroup)
                    groups.remove(i);
            }
            groups.add(newGroup);

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
     * @param newMale пол М/Ж
     * @throws SQLException
     */
    public void set(String nameSname, int day, int month, int year, Male newMale)
            throws SQLException {

        name = nameSname;
        birthday = LocalDate.of(year,month,day);
        male = newMale;

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
        if ((name.equals(teacher.name)) && (birthday.equals(teacher.birthday)))
            return true;
        else
            return false;
    }

    /**
     * Вторая версия одноимённого метода,
     * которая производит поиск по двум критериям
     * @param nameExistingTeacher Ф.И.О
     * @param day дата рождения
     * @param month месяц рождения
     * @param year год рождения
     * @throws SQLException
     */
    public void get(String nameExistingTeacher, int day, int month, int year) throws SQLException {
        Statement state = con.createStatement();
        String select = "SELECT * FROM teacher WHERE name = '" + nameExistingTeacher
                + "' AND birthday = '" + year
                + "-" + month + "-" + day
                + "'";
        ResultSet result = state.executeQuery(select);

        if(result.next()) {
            id = result.getInt(1);
            name = nameExistingTeacher;
            birthday = LocalDate.of(1996, 06, 27);
            if (result.getString(4) == Male.MAN.getValue()) {
                male = Male.MAN;
            } else {
                male = Male.WOMAN;
            }
        }

        String query = "SELECT number FROM `group` JOIN `group_teacher` " +
                "ON `group`.id = `group_teacher`.group_id " +
                "WHERE `group_teacher`.teacher_id = " + id;
        result = state.executeQuery(query);

        while (result.next()) {
            groups.add(new Group(result.getInt("number")));
        }
    }

    /**
     * Проверяет наличие схожей записи по имени и дате рождения
     * @return существует или нет
     * @throws SQLException
     */
    boolean chekAvailible() throws SQLException {
        Teacher teach = new Teacher();
        teach.get(name, birthday.getDayOfMonth(), birthday.getMonthValue(),
                birthday.getYear());
        if (this.equals(teach)) {
            return true;
        } else {
            return false;
        }
    }
}
