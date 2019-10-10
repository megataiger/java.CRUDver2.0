import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;


public class Connect {
    Connect() throws IOException, SQLException {
        File one = new File ("C:\\Users\\NemolyaevIV\\IdeaProjects\\java.CRUDver2.0\\CRUD\\database.properties");
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(one);
        props.load(in);
        in.close();

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        connect = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnect() {
        return connect;
    }

    Connection connect;
}
