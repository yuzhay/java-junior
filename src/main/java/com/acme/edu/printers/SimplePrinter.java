package com.acme.edu.printers;

import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.printers.Printer;

/**
 * Printer interface for Logger
 * Created by Yuriy on 02.11.2015.
 */
public class SimplePrinter implements Printer {

    /**
     * Print message to log
     *
     * @param message print parameter
     */
    @Override
    public void log(String message) throws PrinterException {
        /**
         * Short logical operator ||
         */
        if (message == null || message.isEmpty()) {
            throw new PrinterException("Null or empty Message passed to Printer");
        }
        System.out.println(message);
    }

    @Override
    public void flush() throws PrinterException {
        /*Do nothing*/
    }
}
