package utils;

// main.utils for operations within the main.classes directory

public class classUtils {
    public classUtils() {
    }

    public boolean isLeapYear(int yearNum) {
        if (yearNum % 400 == 0) {
            return true;
        } else if (yearNum % 100 == 0) {
            return false;
        } else if (yearNum % 4 == 0) {
            return true;
        }
        return false;
    }
}