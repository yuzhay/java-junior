package com.acme.edu.states;

import com.acme.edu.decorators.DecoratorCommand;
import com.acme.edu.exceptions.DecoratorException;

/**
 * Logger Integer State class
 * Created by Yuriy on 02.11.2015.
 */
public class IntState extends State {

    //region private fields

    /**
     * Integer is used to determine unset state
     */
    private Integer sum = null;
    //endregion

    //region constructor

    /**
     * Creates new IntState object
     */
    public IntState() {

    }
    //endregion

    //region public methods

    /**
     * Print String parameter to log
     *
     * @param msg print parameter
     */
    @Override
    public void log(String msg, DecoratorCommand decor) throws DecoratorException {
        int value = Integer.parseInt(msg);

        if (sum == null) {
            sum = 0;
        }

        if (value == Integer.MAX_VALUE) {
            decor.decorate(sum.toString());
            sum = Integer.MAX_VALUE;
            return;
        }

        if (isOverflow(value)) {
            decor.decorate(sum.toString());
            sum = value;
        } else {
            sum += value;
        }

        prevDecorator = decor;
    }

    /**
     * Flush log in order to print buffer result
     */
    @Override
    public void flush() throws DecoratorException {

        if (sum != null) {
            prevDecorator.decorate(sum.toString());
            prevDecorator.flush();
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
