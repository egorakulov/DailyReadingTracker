import classes.DatabaseManager;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        try {
            db.connect();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}