import classes.DatabaseManager;
import utils.SqlToJavaStruct;

import java.sql.SQLException;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        try {
            db.connect();
            HashMap<Integer, SqlToJavaStruct> map = db.selectById(1);
            for (int key: map.keySet()) {
                System.out.println(map.get(key).toString());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}