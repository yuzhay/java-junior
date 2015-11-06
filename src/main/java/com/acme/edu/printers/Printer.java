package com.acme.edu.printers;

import com.acme.edu.exceptions.PrinterException;

/**
 * Base Printer interface for SimpleLogger
 * Created by Yuriy on 02.11.2015.
 */
public interface Printer {
    /**
     * Print message to log
     *
     * @param message print parameter
     */
    void log(String message) throws PrinterException;
}
