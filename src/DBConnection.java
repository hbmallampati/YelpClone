import java.sql.*;
import java.sql.DriverManager;

public class DBConnection {

    //private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/yelpdbase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "praneeth18";
    public Connection conn;
    public Statement stmt;
    public ResultSet rslt;


    public static Connection getDBConnection()
    {
        Connection dbConnection = null;

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return dbConnection;

    }


}
