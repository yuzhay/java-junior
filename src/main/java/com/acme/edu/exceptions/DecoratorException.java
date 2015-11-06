package com.acme.edu.exceptions;

import java.util.ArrayList;

/**
 * Created by Yuriy on 05.11.2015.
 */
public class DecoratorException extends Exception {
    private ArrayList<PrinterException> printerExceptions = new ArrayList<PrinterException>();

    public DecoratorException(String message) {
        super(message);
    }

    public void addPrinterException(PrinterException ex) {
        printerExceptions.add(ex);
    }

    public ArrayList<PrinterException> getPrinterExceptionsList() {
        return printerExceptions;
    }

    /**
     * DecoratorException constructor used in Logger class.
     *
     * @param message this message will be shown as exception description
     * @param ex      in case an exception has been thrown in Printer, set this param to PrinterException
     */
    public DecoratorException(String message, PrinterException ex) {
        super(message, ex);
    }
}
