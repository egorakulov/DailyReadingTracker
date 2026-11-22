package utils;

// populated with results of a sql select query
// later parsed into Year, Month, Day, Book classes
public class SqlToJavaStruct {
    public int year;
    public int month;
    public int date;
    public String genre;
    public String bookTitle;
    public String author;

    public SqlToJavaStruct(int year, int month, int date,
           String genre, String bookTitle, String author) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.genre = genre;
        this.bookTitle = bookTitle;
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format("year: %d, month: %d, day: %d, " +
                        "genre: %s, bookTitle: %s, author: %s",
                year, month, date, genre, bookTitle, author);
    }
}
