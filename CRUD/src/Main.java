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
    public static void main(String[] args) throws SQLException {
        Group a = new Group();
        a.get(15);
        a.delete();
    }
}
