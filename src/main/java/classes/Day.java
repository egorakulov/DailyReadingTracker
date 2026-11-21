package classes;
public class Day {
    public boolean read;
    public Book bookRead;
    public int dayNum;

    public Day(int dayNum, boolean read, Book bookRead) {
        this.dayNum = dayNum;
        this.read = read;
        this.bookRead = bookRead;
    }
}