package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    private static String username = "postgres";
    private static String password = "hatikmi";
    private static String url = "jdbc:postgresql://localhost:5432/EasyBank";

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getDBConnection() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;
    }
}
