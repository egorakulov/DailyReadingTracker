package classes;

public class Year {
    public int yearNum;
    public Month[] months;
    public Year(int yearNum) {
        this.yearNum = yearNum;
        populateMonths();
    }

    private void populateMonths() {
        months = new Month[12];
        months[0] = new Month(1, yearNum);
        months[1] = new Month(2, yearNum);
        months[2] = new Month(3, yearNum);
        months[3] = new Month(4, yearNum);
        months[4] = new Month(5, yearNum);
        months[5] = new Month(6, yearNum);
        months[6] = new Month(7, yearNum);
        months[7] = new Month(8, yearNum);
        months[8] = new Month(9, yearNum);
        months[9] = new Month(10, yearNum);
        months[10] = new Month(11, yearNum);
        months[11] = new Month(12, yearNum);
    }
}