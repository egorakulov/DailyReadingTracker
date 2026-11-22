package classes;

import java.sql.*;
import java.util.HashMap;

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

/*
DATABASE ORGANIZATION
tables:
- daily_reading


daily_reading:
int id | int year | int month | int date | String genre | String book_title? | String author?
 */

public class DatabaseManager implements AutoCloseable {
    // url to database. defined in .env
    private static String connectionURL;
    // connection to database
    private Connection conn;
    public DatabaseManager() {
        // get connection String from .env file
        Dotenv dotenv = Dotenv.load();
        this.connectionURL = dotenv.get("SQL_CONNECTION_URL");
    }

    /* connect to database specified by connection string */
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
    --- INSERT STATEMENTS ---
    inserts information into database

    INSERT STATEMENTS FOR daily_reading:
    REQUIRED
    - int year
    - int month
    - int day
    - String genre (of book)
    OPTIONAL
    - String bookTitle
    - String author
     */

    public void insertToTable(int year, int month, int day,
                String genre, String bookTitle, String author)
    throws SQLException{
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
                              String genre)
    throws SQLException {
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

    /*
    --- SELECT STATEMENTS ---
    Select information from database.

    SELECT STATEMENTS FOR daily_reading
    Possible Selection Criteria
    - select by ID
    - select by year
    - select by month (year required)
    - select by day (month, year required)

    PARAMETERS
    - necessary information for chosen select query

    RETURNS:
    - Hashmap of id (int) --> SqlToJavaStruct (defined in SqlToJavaStruct.java)
    client must then parse SqlToJavaStruct to get necessary info
     */
    public HashMap<Integer, SqlToJavaStruct> selectById(int id)
    throws SQLException {
        HashMap<Integer, SqlToJavaStruct> results = new HashMap<>();
        String sql = "SELECT * FROM daily_reading WHERE id=" + id;
        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ) {
            // populate HashMap from rs
            populateMapFromResultSet(results, rs);
        }
        return results;
    }

    public HashMap<Integer, SqlToJavaStruct> selectByYear(int year)
    throws SQLException {
        HashMap<Integer, SqlToJavaStruct> results = new HashMap<>();
        String sql = "SELECT * FROM daily_reading WHERE year=" + year;
        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
                ) {
            populateMapFromResultSet(results, rs);
        }
        return results;
    }

    // update table
    // parameters: year, month, date, genre
    // overload update table
    // parameters: year, month, date, genre, bookTitle, bookAuthor

    // delete from table
    // parameters: year, month, date, genre
    // overload delete from table
    // parameters: year, month, date, genre, bookTitle, bookAuthor

    // have a separate java --> Sql and sql --> java class

    /*
    --- PRIVATE HELPER METHODS ---
     */

    /*
    used in SELECT statements
    creates result hashmap that is returned in a select statement
    from the Result Set of sql query
     */
    private void populateMapFromResultSet(HashMap<Integer,
            SqlToJavaStruct> map, ResultSet rs) throws  SQLException{
        while(rs.next()) {
            int id1 = rs.getInt("id");
            int year = rs.getInt("year");
            int month = rs.getInt("month");
            int date = rs.getInt("date");
            String genre = rs.getString("genre");
            String bookTitle = rs.getString("book_title");
            String author = rs.getString("author");
            SqlToJavaStruct struct = new SqlToJavaStruct(year,
                    month, date, genre, bookTitle, author);
            map.put(id1, struct);
        }
    }
}
