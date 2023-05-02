package salwa.demo4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static Connection con=null;

    private ConnectionDB(Connection con) {
        this.con = con;
    }

    public static Connection verify() throws SQLException, ClassNotFoundException {

        if(con==null)
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/db-project", "root", "");
        }


        return con;
    }
}
