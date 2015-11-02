package com.acme.edu;

/**
 * Smart simple logger.
 * Created by Yuriy on 02.11.2015.
 */
public class SimpleLogger {

    //region private fields
    private State curState;

    private State intState;
    private State stringState;
    private State charState;
    private State boolState;
    //endregion

    //region constructor

    /**
     * Creates SimpleLogger instance
     *
     * @param printer Printer class will be used to format printing result.
     */
    public SimpleLogger(Printer printer) {
        this.intState = new IntState(printer);
        this.stringState = new StringState(printer);
        this.charState = new CharState(printer);
        this.boolState = new BoolState(printer);
        this.curState = null;
    }
    //endregion

    //region public methods

    /**
     * Print integer parameter to log
     *
     * @param message print parameter
     */
    public void log(int message) {
        changeState(intState, String.valueOf(message));
    }

    /**
     * Print String parameter to log
     *
     * @param message print parameter
     */
    public void log(String message) {
        changeState(stringState, message);
    }

    /**
     * Print char parameter to log
     *
     * @param message print parameter
     */
    public void log(char message) {
        changeState(charState, String.valueOf(message));
    }

    /**
     * Print boolean parameter to log
     *
     * @param message print parameter
     */
    public void log(boolean message) {
        changeState(boolState, String.valueOf(message));
    }

    /**
     * Close log in order to print result
     */
    public void close() {
        curState.flush();
    }

    //endregion

    //region private methods
    private void changeState(State st, String message) {
        if (curState != null && curState != st) {
            curState.flush();
        }
        curState = st;
        curState.log(message);
    }

    //endregion
}
