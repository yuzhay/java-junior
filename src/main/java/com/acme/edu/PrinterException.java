package com.acme.edu;

/**
 * Created by Yuriy on 05.11.2015.
 */
public class PrinterException extends Exception {

    /**
     * PrinterException constructor used in Decorator class.
     *
     * @param message this message will be shown as exception description
     */
    public PrinterException(String message) {
        super(message);
    }
}
