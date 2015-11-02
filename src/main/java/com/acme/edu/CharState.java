package com.acme.edu;

/**
 * Logger Char State class
 * Created by Yuriy on 02.11.2015.
 */
public class CharState implements State {
    //region private fields
    private final Printer printer;
    //endregion

    //region constructor
    public CharState(Printer printer) {
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
        printer.log(String.format("%s: %s", Logger.LOG_CHAR, msg));
    }

    /**
     * Flush log in order to print buffer result
     */
    @Override
    public void flush() {
        /*Do nothing*/
    }

    //endregion
}
