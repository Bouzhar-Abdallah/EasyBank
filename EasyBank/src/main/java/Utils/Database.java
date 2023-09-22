package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static void main(String[] args) {
        // Database URL, username, and password
        String url = "jdbc:postgresql://localhost:5432/EasyBank";
        String username = "postgres";
        String password = "hatikmi";

        try {
            // Establish a connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Perform database operations here

            // Close the connection when done
            connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
