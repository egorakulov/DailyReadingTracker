package classes;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;
import utils.SqlToJavaStruct;



/*
--- USAGE ---
encompass all potential database operations within a try - catch block
catch: SQLException e
try:
  1.) connect
  2.) any sql operations you want
 Java will automatically close the connection after try block
 - will call DatabaseManager.close() automatically
 */

public class DatabaseManager implements AutoCloseable {
    private static String connectionURL;
    private Connection conn;
    public DatabaseManager() {
        // get connection String from .env file
        Dotenv dotenv = Dotenv.load();
        this.connectionURL = dotenv.get("SQL_CONNECTION_URL");
    }

    // connect to database specified by connection string
    public void connect() throws SQLException {
        conn = DriverManager.getConnection(connectionURL);
        if (conn != null) {
            DatabaseMetaData dbMeta = conn.getMetaData();
            System.out.println("Driver name: " + dbMeta.getDriverName());
            System.out.println("Connection established.");
        }
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    // drop table tableName from the database
    public void dropTable(String tableName) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try(
            Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
            System.out.printf("Dropped %s table from database.\n", tableName);
        }
    }

    // create a Table in database with name tableName
    public void createTable(String tableName) throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS "
                + tableName + " ("
                + " id INTEGER PRIMARY KEY,"
                + " year INTEGER NOT NULL,"
                + " month INTEGER NOT NULL,"
                + " date INTEGER NOT NULL,"
                + " genre TEXT NOT NULL,"
                + " book_title TEXT,"
                + " author TEXT"
                +");";
        try (
            Statement stmt = conn.createStatement())
        {
            stmt.execute(sql);
            System.out.printf("Executed createTable; built %s table\n", tableName);
        }
    }

    /*
    public void seeAllTables() {
        String sql = "SELECT name FROM sql_master WHERE type=\'table\'";
        try(
            var stmt = conn.createStatement();
        ) {
            stmt.execute(sql);
        }
    }
    */


    public void insertToTable(int year, int month, int day,
                String genre, String bookTitle, String author) throws SQLException{
        String sql = "INSERT INTO daily_reading(year, month, " +
                "date, genre, book_title, author) VALUES(?,?,?,?,?,?)";
        try (
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            pstmt.setInt(3, day);
            pstmt.setString(4, genre);
            pstmt.setString(5, bookTitle);
            pstmt.setString(6, author);
            pstmt.executeUpdate();
        }
    }

    public void insertToTable(int year, int month, int day,
                              String genre) throws SQLException {
        String sql = "INSERT INTO daily_reading(year, month, " +
                "date, genre) VALUES(?,?,?,?)";
        try (
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            pstmt.setInt(3, day);
            pstmt.setString(4, genre);
            pstmt.executeUpdate();
        }
    }

    // SELECT STATEMENTS
    // select statements a hashmap of id --> SqlToJavaStruct

    // update table
    // parameters: year, month, date, genre
    // overload update table
    // parameters: year, month, date, genre, bookTitle, bookAuthor

    // delete from table
    // parameters: year, month, date, genre
    // overload delete from table
    // parameters: year, month, date, genre, bookTitle, bookAuthor

    // have a separate java --> Sql and sql --> java class
}
