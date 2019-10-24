package workWithBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Данный класс определяет способ создания подключения к базе данных
 * Яерез контструктор объекта и последющего вызова функции, которая
 * возвращает объет соединения с базой данных
 */
public class Connect {
    /**
     * Метод открывает файл настроек базы данных,
     * извлекает нужные поля для создания подключения и инициализирует
     * соединение с базой данных
     * @throws IOException
     * @throws SQLException
     */
    Connect() throws IOException, SQLException {
        File one =
                new File ("CRUD" +
                        "\\database.properties");
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(one);
        props.load(in);
        in.close();

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        connect = DriverManager.getConnection(url, username, password);
    }

    /**
     * Метод передаёт объект соединения с базой.
     * @return
     */
    public Connection getConnect() {
        return connect;
    }

    Connection connect;
}
