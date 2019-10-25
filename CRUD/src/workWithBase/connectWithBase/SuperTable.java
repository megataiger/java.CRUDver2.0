package workWithBase.connectWithBase;

import workWithBase.connectWithBase.Connect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Данный класс предназначен для создания подключения
 * к базе данных и дальнейшей передачи его потомкам класса
 * в виде постоянного статического поля
 */
public class SuperTable  {
    protected static Connection con = null;

    static {
        try {
            con = new Connect().getConnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, благодаря которому можно закрыть соединение с базой,
     * причём он будет доступен всем потомкам.
     * @throws SQLException
     */
    public void close() throws SQLException {
        con.close();
    }
}
