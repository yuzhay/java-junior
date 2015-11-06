package com.acme.edu.states;

import com.acme.edu.decorators.DecoratorCommand;
import com.acme.edu.exceptions.DecoratorException;

/**
 * State abstract for Logger
 * Created by Yuriy on 02.11.2015.
 */
public abstract class State {

    protected DecoratorCommand prevDecorator;

    /**
     * Print message to log
     *
     * @param msg print parameter
     */
    abstract void log(String msg, DecoratorCommand decor) throws DecoratorException;

    /**
     * Flush in order to print buffer result to log
     */
    public abstract void flush() throws DecoratorException;

    public State switchState(State newState, String message, DecoratorCommand decor) throws DecoratorException {
        if (this != newState) {
            this.flush();
        }
        prevDecorator = decor;
        newState.log(message, decor);
        return newState;
    }

}
