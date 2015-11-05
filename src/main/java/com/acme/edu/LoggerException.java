package com.acme.edu;

/**
 * Created by Yuriy on 05.11.2015.
 */
public class LoggerException extends Exception {

    public LoggerException(String message) {
        super(message);
    }

    public LoggerException(String message, PrinterException ex) {
        super(message, ex);
    }

    public LoggerException(String message, DecoratorException ex) {
        super(message, ex);
    }
}
