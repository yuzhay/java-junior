package com.acme.edu;

/**
 * State abstract for SimpleLogger
 * Created by Yuriy on 02.11.2015.
 */
public abstract class State {

    protected DecoratorCommand prevDecorator;

    /**
     * Print message to log
     *
     * @param msg print parameter
     */
    abstract void log(String msg, DecoratorCommand decor);

    /**
     * Flush in order to print buffer result to log
     */
    abstract void flush();

    public State switchState(State newState, String message, DecoratorCommand decor) {
        if (this != newState) {
            this.flush();
        }
        prevDecorator = decor;
        newState.log(message, decor);
        return newState;
    }

}
