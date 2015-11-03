package com.acme.edu;

/**
 * Logger String State class
 * Created by Yuriy on 02.11.2015.
 */
public class StringState implements State {

    //region private fields
    private String lastStr = "";
    private int strCounter = 1;
    //endregion

    //region constructor

    /**
     * Creates new StringState object
     */
    public StringState() {

    }
    //endregion

    //region public methods

    /**
     * Print String parameter to log
     *
     * @param message print parameter
     */
    @Override
    public void log(String message, DecoratorCommand decor) {

        if (lastStr == null) {
            lastStr = "";
        }

        if (lastStr.equals(message)) {
            strCounter++;
        } else {
            strCounter = 1;
            if (!lastStr.isEmpty()) {
                decor.decorate(lastStr);
            }
        }

        lastStr = message;
    }

    /**
     * Flush log in order to print buffer result
     */
    @Override
    public void flush(DecoratorCommand decor) {
        if (strCounter > 1) {
            decor.decorate(lastStr + String.format(" (x%d)", strCounter));
            strCounter = 1;
            lastStr = "";
        } else if (strCounter == 1) {
            if (!lastStr.isEmpty()) {
                decor.decorate(lastStr);
            }
            lastStr = "";
        }
    }

    //endregion
}
