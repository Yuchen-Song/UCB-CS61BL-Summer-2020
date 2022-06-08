public class GregorianDate extends Date {

    private static final int[] MONTH_LENGTHS = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public GregorianDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }


    // YOUR CODE HERE

    @Override
    public int dayOfYear() {
        int precedingMonthDays = 0;
        for (int m = 1; m < month; m += 1) {
            precedingMonthDays += getMonthLength(m);
        }
        return precedingMonthDays + dayOfMonth;
    }

    private static int getMonthLength(int m) {
        return MONTH_LENGTHS[m - 1];
    }

    @Override
    public Date nextDate() {
        int monthLength = getMonthLength(this.month);
        if (this.dayOfMonth == monthLength){
            if (this.month == 12){
                GregorianDate ret = new GregorianDate(this.year + 1, 1, 1);
                return ret;
            }else {
                GregorianDate ret = new GregorianDate(this.year, this.month + 1, 1);
                return ret;
            }
        }else {
            GregorianDate ret = new GregorianDate(this.year, this.month, this.dayOfMonth + 1);
            return ret;
        }
    }
}