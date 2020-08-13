import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Database {

    //  Database credentials
    static final String DB_URL = "jdbc:mariadb://localhost:3306/information_schema";
    static final String USER = System.getenv("USER_NAME");
    static final String PASS = System.getenv("USER_PASSWORD");
    static Connection conn;
    static Statement stmt;

    public Database(){
        conn = null;
        stmt = null;
    }


    public static String getTableCreationTime(String query){
        try{
            //Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //Execute a query
            stmt = ((Connection) conn).createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            //get creation time
            String creationTime = rs.getString("create_time");
            //Clean-up environment
            stmt.close();
            conn.close();
            return creationTime;

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return "SQL execution was not succesfull";

        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return "SQL execution was not succesfull";
        }
    }


    public static Map<String, String> getColumnNames(String query){
        Map<String, String> columns = new HashMap<String, String>();
        try{
            //Open a connection
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //Execute a query
            stmt = ((Connection) conn).createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                //get column name and data type
                columns.put(rs.getString("column_name"), rs.getObject("data_type").toString());
            }
            //Clean-up environment
            stmt.close();
            conn.close();
            return columns;

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            System.out.println("SQL execution was not succesfull");
            return new HashMap<String, String>();

        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            System.out.println("SQL execution was not succesfull");
            return new HashMap<String, String>();
        }
    }
}
