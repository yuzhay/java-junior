package com.acme.edu;

/**
 * Logger Boolean State class
 * Created by Yuriy on 02.11.2015.
 */
public class BoolState extends State {
    //region constructor

    /**
     * Creates new BoolState object
     */
    public BoolState() {

    }
    //endregion

    //region public methods

    /**
     * Print String parameter to log
     *
     * @param msg print parameter
     */
    @Override
    public void log(String msg, DecoratorCommand decor) throws PrinterException, DecoratorException {
        decor.decorate(msg);
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
