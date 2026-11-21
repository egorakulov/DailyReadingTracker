package classes;

import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseManager {
    private String connectionURL;
    public DatabaseManager() {

    }

    public static void connect() {
        // get connection string from my .env file
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("SQL_CONNECTION_URL");
        // connect to database specified by connection string
        try(var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("Driver name: " + meta.getDriverName());
                System.out.println("New database created");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
