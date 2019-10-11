/**
 * Данный класс демонстрирует исполнения методов из класса DataBase.
 * @author Илья Немоляев
 * @version 2.0
 */



import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Connection mainCon = new Connect().getConnect();

        ArrayList<Student> students = new ArrayList<Student>();



        mainCon.close();
    }
}
