/**
 * Класс DataBase включает в себя методы для работы с базой данных,
 * состоящую из таблиц student, group, teacher.
 * Данные таблицы реализуют связи student - group многие к одному,
 * group - teacher ногие ко многим.
 * @author Немоляев Илья
 * @version 2.0
 */

import java.lang.*;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.sql.*;
import java.io.*;
import java.util.*;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isDigit;

public class DataBase {

    public DataBase(){}

    /**
     * Метод execute производит вызов в соответствии с конструкцией switch,
     * тем самым обращаясь к закрытым методам класса,
     * реализующие общие запросы к таблицам.
     * @param tablename название таблицы, к которой будет производиться запрос.
     * @param keyQuery ключевое слово запроса.
     * @throws SQLException
     * @throws IOException
     */
    @SecurityConnection(key = true)
    public void execute(String tablename, String keyQuery)
            throws SQLException, IOException {
        Connection con = getConnection("execute");

        switch (keyQuery) {
            case "SELECT" :
                select(tablename, 0, con);
                break;
            case "DELETE" :
                delete(tablename, con);
                break;
            case "INSERT" :
                insert(tablename, con);
                break;
            case "UPDATE" :
                update(tablename, con);
                break;
            default :
                    System.out.println("Неверный ключ для запроса!");
        }
    }

    /**
     * Метод executeForTGTables производит вызов в соответствии
     * с конструкцией switch, тем самым обращаясь к закрытым методам класса,
     * реализующие запросы на вставку или удаление связи многие ко многим
     * таблиц group и teacher.
     * @param tablename название таблицы, от которого зависит,
     *                  какую запись мы хотим добавить:
     *                  группу преподавателю или преподавателя группе.
     *                  У одной группы может быть много преподавателей,
     *                  а у преподавателя может быть много групп.
     * @param keyQuery ключ для выбора запроса на вставку или удаления.
     * @throws SQLException
     * @throws IOException
     */
    @SecurityConnection(key = true)
    public void executeForTGTables(String tablename, String keyQuery)
            throws SQLException, IOException {
        Connection con = getConnection("executeForTGTables");

        if (tablename.equals("teacher")){
            switch (keyQuery) {
                case "ADD" :
                    addGroupForTeacher(con);
                    break;
                case "DELETE" :
                    deleteGroupForTeacher(con);
            }
        }
        else if (tablename.equals("group")){
            switch (keyQuery) {
                case "ADD" :
                    addTeacherForGroup(con);
                    break;
                case "DELETE" :
                    deleteTeacherForGroup(con);
                default :
                    System.out.println("Неверный ключ для запроса!");
            }
        }
    }

    /**
     * Метод executeSelectWithId производит вызов
     * в соответствии с конструкцией switch,
     * тем самым обращаясь к закрытым методам класса,
     * реализующие запросы на выборку отдельных строк и
     * вывод дополнительной информации.
     * @param tablename название таблицы, для которой будет производиться
     *                  выборка.
     * @param id уникальный номер строки таблицы.
     * @throws SQLException
     * @throws IOException
     */
    @SecurityConnection(key = true)
    public void executeSelectWithId(String tablename, int id)
            throws SQLException, IOException {
        Connection con = getConnection("executeSelectWithId");

        switch (tablename) {
            case "student" :
                select(tablename, id, con);
                break;
            case "teacher" :
                selectGroupsForTeacher(id, con);
                break;
            case "group" :
                selectTeachersForGroup(id, con);
                break;
            case "groupStudents" :
                selectStudentsFromGroup(id, con);
                break;
            default :
                System.out.println("Неверный ключ для запроса!");
        }
    }

    /**
     * Метод select производит выборку из таблицы по уникальному номеру строки
     * или всех строк.
     * @param tablename название таблицы.
     * @param id уникальный номер.
     * @param con объект содениения с базой данных
     * @throws SQLException
     */
    private void select(String tablename, int id, Connection con)
            throws SQLException {
        String query = "SELECT * FROM `" + tablename + "`";

        if (id == 0){
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            printResult(result);
        } else {
            String columnName = getIdColumnName(tablename, con);
            query += " WHERE `" + tablename + "`.`" + columnName + "` = " + id;
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            printResult(result);
        }

        con.close();
    }

    /**
     * Метод delete удаляет из таблицы строку, заданную пользователем.
     * @param tablename название таблицы.
     * @param con объект соединения с базой данных.
     * @throws SQLException
     */
    private void delete(String tablename, Connection con)
            throws SQLException {
        String columnName = getIdColumnName(tablename, con);
        String query = "DELETE FROM " + tablename + " WHERE "
                + tablename + "." + columnName + " = ?";
        PreparedStatement prstate = con.prepareStatement(query);

        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        in.reset();

        prstate.setInt(1, number);
        prstate.executeUpdate();

        con.close();
    }

    /**
     * Метод insert вставляет строку в таблицу.
     * @param tablename название таблицы.
     * @param con объект соединения с базой данных.
     * @throws SQLException
     */
    private void insert(String tablename, Connection con)
            throws SQLException {
        String query = "INSERT INTO `" + tablename + "` (";
        Statement stat = con.createStatement();
        ResultSet result = stat.executeQuery("SELECT * FROM `" + tablename + "`");
        ResultSetMetaData rm = result.getMetaData();
        for (int i = 1; i <= rm.getColumnCount(); i++) {
            if (i == rm.getColumnCount()) {
                query += rm.getColumnName(i) + ") ";
            } else {
                query += rm.getColumnName(i) + ", ";
            }
        }

        query += "VALUES (";
        for (int i = 1; i <= rm.getColumnCount(); i++) {
            if (i == 1) {
                query += "NULL, ";
            } else if (i == rm.getColumnCount()) {
                query += "?)";
            } else {
                query += "?, ";
            }
        }

        PreparedStatement prstate = con.prepareStatement(query);

        Scanner in = new Scanner(System.in);
        for (int i = 2; i <= rm.getColumnCount(); i++) {
            System.out.print("Введите значение поля " + rm.getColumnName(i) + ": ");
            String value = in.nextLine();
            prstate.setString(i-1, value);
        }

        prstate.executeUpdate();

        in.reset();
        con.close();
    }

    /**
     * Метод update изменяет строку в таблице.
     * Пользователь выбирает нужную строку, а также атрибуты,
     * подлежащие изменению.
     * @param tablename название таблицы.
     * @param con объект соединения с базой данных
     * @throws SQLException
     */
    private void update(String tablename, Connection con)
            throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE `" + tablename + "` SET ");
        Statement stat = con.createStatement();
        ResultSet result = stat.executeQuery("SELECT * FROM `" + tablename + "`");
        ResultSetMetaData rm = result.getMetaData();

        Scanner in = new Scanner(System.in);
        System.out.print("Введите ID изменяемой строки: ");
        String id = in.nextLine();

        for (int i = 1; i <= rm.getColumnCount(); i++) {
            System.out.print(i + " " + rm.getColumnName(i) + "\t");
        }

        System.out.print("\nВведите через пробел номера полей, которые вы хотите изменить: ");
        String numbersField = in.nextLine();

        ArrayList values = new ArrayList();

        for (char a : numbersField.toCharArray()) {
            if (isDigit(a)) {
                int i = getNumericValue(a);
                query.append(rm.getColumnName(i) + " = ?, ");
                System.out.print("Введите значение поля " + rm.getColumnName(i)
                        + ": ");
                values.add(in.nextLine());
             }
        }

        query.delete(query.length()-2, query.length());
        query.append(" WHERE `" + tablename + "`.`" + rm.getColumnName(1)
                + "` = " + id);

        result.close();
        stat.close();
        in.reset();

        PreparedStatement prstate = con.prepareStatement(query.toString());
        for (int i = 1; i <= values.size(); i++) {
            prstate.setString(i, ((String) values.get(i-1)));
        }

        prstate.executeUpdate();

        prstate.close();
        con.close();
    }

    /**
     * Метод addTeacherForGroup добавляет преподавателя группе.
     * @param con объект соединения с базой данных.
     * @throws SQLException
     */
    private void addTeacherForGroup(Connection con)
            throws SQLException {
        System.out.print("Введите номер группы: ");
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();

        Statement state = con.createStatement();
        number = getIdStringTableGroup(state, number);

        ResultSet result = resultTeachersForGroup(state, number);
        printResult(result);

        System.out.println("\n Преподаватели");
        result = state.executeQuery("SELECT `id`, `name` FROM `teacher`");
        printResult(result);

        System.out.println("Введите id преподавателя, которого хотите "
                + "присвоить группе: ");
        int teacherId = in.nextInt();
        state.executeUpdate("INSERT INTO `group/teacher` "
                + "(`group_id`,`teacher_id`) VALUES (" + number + ","
                + teacherId + ")");

        result = resultGroupsForTeacher(state, number);
        printResult(result);

        in.reset();
        result.close();
        state.close();
        con.close();
    }

    /**
     * Метод deleteTeacherForGroup удаляет группе преподавателя.
     * @param con объект соединения с базой данных
     * @throws SQLException
     */
    private void deleteTeacherForGroup(Connection con)
            throws SQLException {
        System.out.print("Введите номер группы: ");
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();

        Statement state = con.createStatement();
        number = getIdStringTableGroup(state, number);
        ResultSet result = resultTeachersForGroup(state, number);
        printResult(result);

        System.out.println("\n Преподаватели");
        result = state.executeQuery("SELECT `id`, `name` FROM `teacher`");
        printResult(result);

        System.out.println("Введите id преподавателя, которого хотите "
                + "присвоить группе: ");
        int teacherId = in.nextInt();

        state.executeUpdate("DELETE FROM `group/teacher` WHERE "
                + "`group/teacher`.`teacher_id` = " + teacherId);
        result = resultTeachersForGroup(state, number);
        printResult(result);

        result.close();
        state.close();
        con.close();
        in.reset();
    }

    /**
     * Метод addGroupForTeacher добавляет группу преподавателю.
     * @param con объект соединения с базой данных.
     * @throws SQLException
     */
    private void addGroupForTeacher(Connection con)
            throws SQLException {
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery("SELECT `id`, `name` FROM "
                + "`teacher`");
        printResult(result);

        System.out.print("Выберите преподавателя, введя id");
        Scanner in = new Scanner(System.in);
        int teacherId = in.nextInt();

        result = resultGroupsForTeacher(state, teacherId);
        printResult(result);

        System.out.println("Группы");
        result = state.executeQuery("SELECT `number` FROM `group`");
        printResult(result);

        System.out.print("Введите номер группы: ");
        int numberGroup = in.nextInt();

        state.executeUpdate("INSERT INTO `group/teacher`"
                + "(`group_id`,`teacher_id`) VALUES ("
                + getIdStringTableGroup(state,numberGroup) + ","
                + teacherId + ")");

        result = resultGroupsForTeacher(state, teacherId);
        printResult(result);

        result.close();
        state.close();
        con.close();
        in.close();
    }

    /**
     * Метод deleteGroupForTeacher удаляет группу преподавателю.
     * @param con объект соединения с базой данных.
     * @throws SQLException
     */
    private void deleteGroupForTeacher(Connection con)
            throws SQLException {
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery("SELECT `id`, `name` FROM "
                + "`teacher`");
        printResult(result);

        System.out.print("Выерите преподавателя, введя id");
        Scanner in = new Scanner(System.in);
        int teacherId = in.nextInt();

        result = resultGroupsForTeacher(state, teacherId);
        printResult(result);

        System.out.print("Введите номер группы: ");
        int numberGroup = in.nextInt();

        state.executeUpdate("DELETE FROM `group/teacher` "
                + "WHERE `group/teacher`.`group_id` = "
                + getIdStringTableGroup(state,numberGroup));

        result = resultGroupsForTeacher(state, teacherId);
        printResult(result);

        result.close();
        state.close();
        con.close();
        in.reset();
    }

    /**
     * Метод selectTeachersForGroup делает выборку преподавателей,
     * которые преподают в группе, заданной пользователем.
     * @param id уникальный номер группы.
     * @param con объект соединения с базой данных.
     * @throws SQLException
     */
    private void selectTeachersForGroup(int id, Connection con)
            throws SQLException {
        Statement state = con.createStatement();
        ResultSet individString = state.executeQuery("SELECT * FROM `group` "
                + "WHERE `group`.`id` = " + id);
        printResult(individString);
        individString.close();

        ResultSet result = resultTeachersForGroup(state, id);
        printResult(result);

        con.close();
    }

    /**
     * Метод selectGroupsForTeacher делает выборку групп,
     * у которых ведёт заданный преподаватель.
     * @param id уникальный номер преподавтеля.
     * @param con объект соединения с базой данных.
     * @throws SQLException
     */
    private void selectGroupsForTeacher(int id, Connection con)
            throws SQLException {
        Statement state = con.createStatement();
        ResultSet individString = state.executeQuery("SELECT * FROM `teacher` "
                + "WHERE `teacher`.`id` = " + id);
        printResult(individString);
        individString.close();

        ResultSet result = resultGroupsForTeacher(state, id);
        printResult(result);

        con.close();
    }

    /**
     * Метод selectStudentsFromGroup делает выборку студентов, обучающихся
     * в заданной группе.
     * @param numberGroup номер группы.
     * @param con объект соединения с базой данных.
     * @throws SQLException
     */
    private void selectStudentsFromGroup(int numberGroup, Connection con)
            throws SQLException {
        System.out.println("Студенты группы " + numberGroup);
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery("SELECT `id` FROM `group` "
                + "WHERE `group`.`number` = " + numberGroup);

        if (result.next()) {
            numberGroup = result.getInt(1);
        }

        result = state.executeQuery("SELECT * FROM `student` "
                + "WHERE `student`.`group_id` = " + numberGroup);
        printResult(result);

        con.close();
    }

    /**
     * Метод getIdColumnName возвращает имя первой колонки таблицы,
     * которая зачастую является колонкой уникальных номеров строк.
     * @param tablename название таблицы.
     * @param con объект соединения с базой данных.
     * @return название первого атрибута таблицы.
     * @throws SQLException
     */
    private String getIdColumnName (String tablename, Connection con)
            throws SQLException{
        Statement state = con.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM " + tablename);

        ResultSetMetaData rm = result.getMetaData();
        String name = rm.getColumnName(1);

        state.close();

        return name;
    }

    /**
     * Метод printResult выводит в консоль все данные из объекта ResultSet,
     * который хранит в себе результат запроса к базе данных.
     * @param result объект, хранящий в себе результат запроса.
     * @throws SQLException
     */
    private void printResult (ResultSet result)
            throws SQLException{
        ResultSetMetaData rm = result.getMetaData();
        for (int i = 1; i <= rm.getColumnCount(); i++){
            System.out.printf( "%-30s",rm.getColumnName(i));
        }
        System.out.print("\n");
        while (result.next()){
            for (int i = 1; i <= rm.getColumnCount(); i++){
                System.out.printf("%-30s",result.getString(i));
            }
            System.out.print("\n");
        }
    }

    /**
     * Метод resultTeachersForGroup выполняет выборку с соединением таблиц.
     * Основой для соединения связь группа-учителя.
     * @param state объект, для выполнения запроса.
     * @param numberGroup уникальный номер группы.
     * @return объект ResultSet, в котором содержится резульат выборки.
     * @throws SQLException
     */
    private ResultSet resultTeachersForGroup(Statement state, int numberGroup)
            throws SQLException {
        ResultSet result = state.executeQuery("SELECT `teacher_id`, `name`" +
                "FROM `group/teacher` JOIN `teacher`" +
                "ON `group/teacher`.`teacher_id` = `teacher`.`id`" +
                "WHERE `group/teacher`.`group_id` = " + numberGroup);

        return result;
    }

    /**
     * Метод resultGroupsForTeacher выполняет выборку с соединением таблиц.
     * Основой для соединения является связь преподаватель-группы.
     * @param state объект для выполнения запроса.
     * @param teacherId уникальный номер преподавателя.
     * @return объект ResultSet, в котором хранится результат выборки.
     * @throws SQLException
     */
    private ResultSet resultGroupsForTeacher(Statement state, int teacherId)
            throws SQLException {
        ResultSet result = state.executeQuery("SELECT `number` " +
                "FROM `group` JOIN `group/teacher`" +
                "ON `group`.`id` = `group/teacher`.`group_id`" +
                "WHERE `group/teacher`.`teacher_id` = " + teacherId);

        return result;
    }

    /**
     * Метод getIdStringTableGroup по номеру группы возвращает
     * её уникальный номер.
     * @param state объект для выполнения запроса.
     * @param number номер группы.
     * @return уникальный номер группы.
     * @throws SQLException
     */
    private int getIdStringTableGroup(Statement state, int number)
            throws SQLException {
        ResultSet result = state.executeQuery("SELECT `id` FROM `group` "
                + "WHERE `number` = " + number);

        if (result.next())
            number = result.getInt(1);

        return number;
    }

    /**
     * Метод getConnection считывает данные из файла database.property,
     * после чего выполняет соединения с базой данных с учётом выполнения условия.
     * @param methodName название метода, по которому проверяется условие.
     *                   Метод, который имеет аннотацию SecurityConnection,
     *                   а так же удовлетворяет условию, может получить объект
     *                   соединения с базой данных.
     * @return объект соединения с базой данных.
     * @throws SQLException
     * @throws IOException
     */
    private Connection getConnection(String methodName) throws SQLException, IOException {
        Properties props = new Properties();
        FileInputStream in = new FileInputStream("database.properties");
        props.load(in);
        in.close();

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        Connection con = null;

        for (Method m : DataBase.class.getMethods()){
            if (methodName.equals(m.getName())) {
                SecurityConnection a = m.getAnnotation(SecurityConnection.class);
                if (a.key()) {
                    con = DriverManager.getConnection(url, username, password);
                }
                break;
            }
        }

        return con;
    }
}

/**
 * Аннотация SecurityConnection предназначена для последующей проверки
 * её наличия и значения поля key у методов класса данного пакета.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface SecurityConnection {
    boolean key() default false;
}
