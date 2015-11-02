package com.acme.edu;

/**
 * Logger String State class
 * Created by Yuriy on 02.11.2015.
 */
public class StringState implements State {

    //region private fields
    private Printer printer;
    private String lastStr = "";
    private int strCounter = 1;
    //endregion

    //region constructor

    /**
     * Creates new StringState object
     *
     * @param printer is used to format printing string
     */
    public StringState(Printer printer) {
        this.printer = printer;
    }
    //endregion

    //region public methods

    /**
     * Print String parameter to log
     *
     * @param message print parameter
     */
    @Override
    public void log(String message) {

        if (lastStr == null) {
            lastStr = "";
        }

        if (lastStr.equals(message)) {
            strCounter++;
        } else {
            strCounter = 1;
            if (!lastStr.isEmpty()) {
                printer.log(String.format("%s: %s", Logger.LOG_STRING, lastStr));
            }
        }

        lastStr = message;
    }

    /**
     * Flush log in order to print buffer result
     */
    @Override
    public void flush() {
        if (strCounter > 1) {
            printer.log(String.format("%s: %s (x%d)", Logger.LOG_STRING, lastStr, strCounter));
            strCounter = 1;
            lastStr = "";
        } else if (strCounter == 1) {
            if (!lastStr.isEmpty()) {
                printer.log(String.format("%s: %s", Logger.LOG_STRING, lastStr));
            }
            lastStr = "";
        }
    }

    //endregion
}
