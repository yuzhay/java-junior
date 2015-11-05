package com.acme.edu;

/**
 * Created by Yuriy on 05.11.2015.
 */
public class DecoratorException extends Exception {
    public DecoratorException(String message) {
        super(message);
    }

    /**
     * DecoratorException constructor used in SimpleLogger class.
     *
     * @param message this message will be shown as exception description
     * @param ex      in case an exception has been thrown in Printer, set this param to PrinterException
     */
    public DecoratorException(String message, PrinterException ex) {
        super(message, ex);
    }
}
