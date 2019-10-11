import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

    public void close() throws SQLException {
        con.close();
    }
}
