package com.acme.edu.exceptions;

import com.acme.edu.exceptions.DecoratorException;

import java.io.IOException;

/**
 * Created by Yuriy on 05.11.2015.
 */
public class LoggerException extends Exception {

    /**
     * LoggerException constructor used in Logger class.
     *
     * @param message this message will be shown as exception description
     */
    public LoggerException(String message) {
        super(message);
    }

    /**
     * LoggerException constructor used in Logger class.
     *
     * @param message this message will be shown as exception description
     * @param ex      in case an exception has been thrown in Decorator, set this param to DecoratorException
     */
    public LoggerException(String message, DecoratorException ex) {
        super(message, ex);
    }

    public LoggerException(String message, IOException e) {
        super(message, e);
    }

    public LoggerException(String s, PrinterException e) {
        super(s, e);
    }
}
