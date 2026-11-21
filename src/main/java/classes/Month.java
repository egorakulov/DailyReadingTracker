package classes;

import utils.classUtils;

public class Month {
    public String monthName;
    int monthNum;
    // size of readingHistory depends on the month:
    // ex: January readingHistory has size 31, April has size 30
    public Day[] readingHistory;
    public Month(int monthNum, int yearNum) {
        int numDays = calculateDays(monthNum, yearNum);
        readingHistory = new Day[numDays];
        this.monthName = calculateMonthName(monthNum);
        this.monthNum = monthNum;
    }


    private int calculateDays(int monthNum, int yearNum) {
        int numDays = 0;
        if (monthNum == 1 || monthNum == 3 || monthNum == 5 ||
                monthNum == 7 || monthNum == 8 || monthNum == 10 ||
                monthNum == 12) {
            numDays = 31;
        } else if (monthNum == 2) {
            numDays = (new classUtils().isLeapYear(yearNum)) ? 29 : 28;
        } else {
            numDays = 30;
        }
        return numDays;
    }

    private String calculateMonthName(int monthNum) {
        if (monthNum == 1) {
            return "January";
        } else if (monthNum == 2) {
            return "February";
        } else if (monthNum == 3) {
            return "March";
        } else if (monthNum == 4) {
            return "April";
        }else if (monthNum == 5) {
            return "May";
        }else if (monthNum == 6) {
            return "June";
        }else if (monthNum == 7) {
            return "July";
        }else if (monthNum == 8) {
            return "August";
        }else if (monthNum == 9) {
            return "September";
        }else if (monthNum == 10) {
            return "October";
        }else if (monthNum == 11) {
            return "November";
        }else if (monthNum == 12) {
            return "December";
        }
        return "INVALID_MONTH";
    }
}