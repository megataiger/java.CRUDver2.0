import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Класс предназначен для абстракции записей о группах и работы с ними
 */
public class Group extends SuperTable {
    /**
     * Пустой конструктор служит для дальнейшей инициализации уже имеющейся
     * строки в таблице.
     */
    Group() {
    }

    /**
     * Создаёт объект группа с её фактическим номером.
     * @param i фактический номер группы
     */
    Group(int i) {
        number = i;
    }

    /**
     * Метод добавляет запись в базу о новой группе, добавленной пользователем.
     * @throws SQLException
     */
    public void add() throws SQLException {
        Statement state = con.createStatement();
        String query = "INSERT INTO `group` " +
                "(id, number) VALUES (null, '" + number + "')";
        state.executeUpdate(query);
        getId(number);
    }

    /**
     * Изменяет фактический номер группы объекта,
     * а затем вызывает метод, который делает тоже самое в таблице.
     * @param temp новый номер группы
     * @throws SQLException
     */
    public void set(int temp) throws SQLException {
        number = temp;
        update();
    }

    /**
     * Метод на основе фактического номер группы, получает данные
     * из атрибутов соответсвующей строки и записывает их в поля объекта.
     * @param temp фактический номер группы
     * @throws SQLException
     */
    public void get(int temp) throws SQLException {
        getId(temp);
        if (id > 0) {
            number = temp;
        } else {
            getId(number);
        }
    }

    /**
     * Присваивает значение уникального номер строки полю объекта
     * @param number
     * @throws SQLException
     */
    void getId(int number) throws SQLException {
        Statement state = con.createStatement();
        String select = "SELECT * FROM `group` WHERE number = " + number;

        ResultSet idResult = state.executeQuery(select);
        if (idResult.next()) {
            id = idResult.getInt(1);
        }
    }

    /**
     * Метод для использования в других классах.
     * @return значение поля id.
     */
    public int getId() {
        return id;
    }

    /**
     * Изменяет атрибуты соответсвующей строки используя поля объекта
     * @throws SQLException
     */
    void update() throws SQLException {
        Statement state = con.createStatement();
        String update = "UPDATE `group` SET number = '"
                + number + "' WHERE id = " + id;
        state.executeUpdate(update);
    }

    /**
     * Удаляет запись о группе в баз данных и отчищает коллекции связей,
     * чтобы освободить память
     * @throws SQLException
     */
    void delete() throws SQLException {
        Statement state = con.createStatement();
        String delete = "UPDATE student SET group_id = NULL WHERE group_id = "
                + id;
        state.executeUpdate(delete);

        delete = "DELETE FROM `group/teacher` WHERE group_id = " + id;
        state.executeUpdate(delete);

        delete = "DELETE FROM `group` WHERE id = " + id;
        state.executeUpdate(delete);

        for(int i = 0 ; i < teachers.size() ; i++){
            teachers.remove(i);
        }
        for(int i = 0 ; i < students.size() ; i++){
            teachers.remove(i);
        }
    }

    /**
     * Выводит в консоль фактический номер группы
     */
    void info(){
        System.out.println(number);
    }

    /**
     * Метод получает списко преподавтелей группы из таблицы,
     * после чего инициализирует объекты Teacher и записывает их
     * в поле-коллекцию. Выводит в консоль список.
     * @throws SQLException
     */
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

    /**
     * Сравнивает два объекта
     * @param group объект, с которым сравниваем
     * @return
     */
    public boolean equals(Group group) {
        if (number == group.number) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Добавляет учителя группе, делая запись в базу,
     * а затем добавляет его в коллекцию.
     * @param teacher объект Teacher
     * @throws SQLException
     */
    public void addTeacher(Teacher teacher) throws SQLException {
        Statement state = con.createStatement();
        String insert = "INSERT INTO `group/teacher` " +
                "(group_id, teacher_id) VALUES " +
                "(" + id + ", " + teacher.getId() + ")";

        state.executeUpdate(insert);
        teachers.add(teacher);
    }

    /**
     * Удаляет запись о преподаватели группы и убирает его из коллекции
     * @param teacher объект Teacher
     * @throws SQLException
     */
    public void deleteTeacher(Teacher teacher) throws SQLException {
        Statement state = con.createStatement();
        String delete = "DELETE FROM `group/teacher` WHERE group_id = " + id
                + " AND teacher_id = " + teacher.getId();
        state.executeUpdate(delete);

        for (int i = 0 ; i < teachers.size() ; i++ ) {
            if (teachers.get(i) == teacher) {
                teachers.remove(i);
            }
        }
    }

    /**
     * Меняет группе преподавтеля на основе айди и связи таблиц.
     * Коллекция так же меняется.
     * @param oldTeacher старый преподаватель
     * @param newTeacher новый преподаватель
     * @throws SQLException
     */
    public void setTeacher (Teacher oldTeacher, Teacher newTeacher) throws SQLException {
        Statement state = con.createStatement();
        String update = "UPDATE `group/teacher` SET teacher_id = "
                + newTeacher.getId()
                + " WHERE group_id = " + id + " AND teacher_id = "
                + oldTeacher.getId();
        state.executeUpdate(update);

        for (int i = 0 ; i < teachers.size() ; i++)
            if (teachers.get(i) == oldTeacher)
                teachers.set(i, newTeacher);
    }

    /**
     * Метод получает список студентов группы из таблицы,
     * после чего инициализирует объекты Student и записывает их
     * в поле-коллекцию. Выводит в консоль список.
     * @throws SQLException
     */
    public void studentList () throws SQLException{
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
