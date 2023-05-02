package salwa.demo4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginControl {
    Statement st;
    public boolean exist(Admin admin) throws SQLException, ClassNotFoundException {
        st = ConnectionDB.verify().createStatement();

        String req = "select * from admins where username = '"+admin.getUsername()+"' and password = '"+admin.getPwd()+"'";
        ResultSet rs = st.executeQuery(req);

        if (rs.next())
            return true;
        return false;
    }
}
