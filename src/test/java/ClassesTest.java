
import classes.Year;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClassesTest {

    @Test
    public void monthsTest() {
        // testing that months get the correct number of dates
        Year x2025 = new Year(2025);
        assertTrue(x2025.months[0].readingHistory.length == 31);
        assertTrue(x2025.months[1].readingHistory.length == 28);
        assertTrue(x2025.months[2].readingHistory.length == 31);
        assertTrue(x2025.months[3].readingHistory.length == 30);
        assertTrue(x2025.months[4].readingHistory.length == 31);
        assertTrue(x2025.months[5].readingHistory.length == 30);
        assertTrue(x2025.months[6].readingHistory.length == 31);
        assertTrue(x2025.months[7].readingHistory.length == 31);
        assertTrue(x2025.months[8].readingHistory.length == 30);
        assertTrue(x2025.months[9].readingHistory.length == 31);
        assertTrue(x2025.months[10].readingHistory.length == 30);
        assertTrue(x2025.months[11].readingHistory.length == 31);

        Year x2024 = new Year(2024);
        assertTrue(x2024.months[1].readingHistory.length == 29);

        Year x1900 =  new Year(1900);
        assertTrue(x1900.months[1].readingHistory.length == 28);

        Year x2000 = new Year(2000);
        assertTrue(x2000.months[1].readingHistory.length == 29);
    }
}
