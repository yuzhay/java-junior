package com.acme.edu;

/**
 * Printer interface for SimpleLogger
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
}
