package com.acme.edu.exceptions;

import java.util.ArrayList;

/**
 * Created by Yuriy on 05.11.2015.
 */
public class DecoratorException extends Exception {
    private final ArrayList<PrinterException> printerExceptions = new ArrayList<PrinterException>();

    /**
     * Decorates String message
     *
     * @param message param to decorate
     */
    public DecoratorException(String message) {
        super(message);
    }

    /**
     * Adds new PrinterException to list. You can use this list in the feature for details.
     * @param ex Exception to add
     */
    public void addPrinterException(PrinterException ex) {
        printerExceptions.add(ex);
    }

    /**
     * Getter for PrinterExceptionsList
     * @return
     */
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
