package com.acme.edu;

/**
 * Logger Integer State class
 * Created by Yuriy on 02.11.2015.
 */
public class IntState implements State {

    //region private fields
    private Printer printer;

    /**
     * Integer is used to determine unset state
     */
    private Integer sum = null;
    //endregion

    //region constructor
    public IntState(Printer printer) {
        this.printer = printer;
    }
    //endregion

    //region public methods

    /**
     * Print String parameter to log
     *
     * @param msg print parameter
     */
    @Override
    public void log(String msg) {
        int value = Integer.parseInt(msg);

        if (sum == null) {
            sum = 0;
        }

        if (value == Integer.MAX_VALUE) {
            printer.log(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = Integer.MAX_VALUE;
            return;
        }

        if (isOverflow(value)) {
            printer.log(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = value;
        } else {
            sum += value;
        }
    }

    /**
     * Flush log in order to print buffer result
     */
    @Override
    public void flush() {
        if (sum != null) {
            printer.log(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = null;
        }
    }
    //endregion

    //region private methods
    private boolean isOverflow(int message) {
        return message + (long) sum > Integer.MAX_VALUE;
    }
    //endregion
}
