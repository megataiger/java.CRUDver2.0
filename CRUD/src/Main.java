/**
 * Данный класс демонстрирует исполнения методов из класса DataBase.
 * @author Илья Немоляев
 * @version 2.0
 */

import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            //Создаём экземпляр объекта DataBase, для доступа к методам класса.
            DataBase a = new DataBase();

            //Добавляем новую группу в таблицу group
            a.execute("group", "INSERT");
            //Добавляем нового студента
            a.execute("student", "INSERT");
            //Просматриваем список преподавателей
            a.execute("teacher", "SELECT");
            //Добавляем группе преподавателя
            a.executeForTGTables("group", "ADD");
            //Просматриваем список групп, у которых ведёт преподаватель с id=1
            a.executeSelectWithId("teacher", 1);
            //Просматриваем список преподавателей у группы с id=1
            a.executeSelectWithId("group", 1);
            //Просматриваем информацию о студенте с id=1
            a.executeSelectWithId("student", 1);
        }
        catch (SQLException e)
        {
            System.out.println("Возникла ошибка с базой данных");
        }
        catch (IOException e)
        {
            System.out.println("Возникла ошибка с получением настроек соединения");
        }
    }
}
