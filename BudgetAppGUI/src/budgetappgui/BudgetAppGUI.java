package budgetappgui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BudgetAppGUI {

    static final String DB_URL = "jdbc:derby://localhost:1527/BudgetAppDB";
    static final String DB_USER = "appuser";
    static final String DB_PASSWD = "appuser";
    private static DBBean db = new DBBean();
    private static ResultSet resultSet;
   
    public static void main(String[] args) throws SQLException {
        String query = "Select * from users";
            Connection con = null;
            try {
                con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
            } catch (SQLException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            resultSet = db.doQuery(query, con);
            
            if(resultSet.next() == false){
                String query2 = "Insert into users (username, password, usertype) values ('ADMIN', 'ADMIN','ADMINUSER')";
                db.doQuery(query2, con);
            }
        LoginFrame login = new LoginFrame();
        login.setVisible(true);          
    }

}
