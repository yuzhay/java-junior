package com.acme.edu;

/**
 * Logger Boolean State class
 * Created by Yuriy on 02.11.2015.
 */
public class BoolState implements State {
    //region private fields
    private final Printer printer;
    //endregion

    //region constructor

    /**
     * Creates new BoolState object
     *
     * @param printer is used to format printing bool
     */
    public BoolState(Printer printer) {
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
        printer.log(String.format("%s: %s", Logger.LOG_PRIMITIVE, msg));
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
