package com.acme.edu.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;

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

    public PrinterException(String message, IOException ex) {
        super(message, ex);
    }

    public PrinterException(String message, FileNotFoundException ex) {
        super(message, ex);
    }

}
