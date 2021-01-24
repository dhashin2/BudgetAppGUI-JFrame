package weSave;

import java.sql.*;
import java.util.logging.*;

public class DBBean {
    
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    public ResultSet doQuery(String query, Connection con) {
        try {
            statement = con.createStatement();
            String first_word = query.substring(0, query.indexOf(' ')); 
            if (first_word.equalsIgnoreCase("SELECT")) {
                resultSet = DQL_query(query, statement);
                return resultSet;
            }
            else if (first_word.equalsIgnoreCase("DELETE") || first_word.equalsIgnoreCase("INSERT") || first_word.equalsIgnoreCase("UPDATE")) {
                DML_query(query, statement);
            }
            statement.close(); 		                                  
        }
        catch (SQLException s){
            System.out.println("SQL statement is not executed!");
            s.printStackTrace();
        }
        return resultSet;
    }
    
    // SELECT QUERIES
    private static ResultSet DQL_query(String query, Statement statement) {
        ResultSet resultSet = null;
        try  {
            resultSet = statement.executeQuery(query);
        }
        catch (SQLException s){
            s.printStackTrace();
            return resultSet;
        }
        return resultSet;
    }
    
    // DELETE / INSERT/ UPDATE Queries
    private static boolean DML_query(String query, Statement statement) {
        try  {
            statement.executeUpdate(query);
        }
        catch (SQLException s){
            s.printStackTrace();
            return false;
        }
        return true;
    }
    
    // create a string from resultset
    public String resultSet_to_String(ResultSet resultSet) {
        String res = "Results: ";
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) res = res + ", ";
                    String columnValue = resultSet.getString(i);
                    res = res + rsmd.getColumnName(i) + ": " + columnValue;
                }
                res = res + "\n";
            }
        } catch (SQLException s){
            res = s.getMessage();
            //s.printStackTrace();
        }
        return res;
    }
}

