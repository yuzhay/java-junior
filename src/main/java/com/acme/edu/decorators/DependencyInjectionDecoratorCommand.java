package com.acme.edu.decorators;

import com.acme.edu.exceptions.DecoratorException;
import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.printers.Printer;

/**
 * Created by Yuriy on 06.11.2015.
 */
public abstract class DependencyInjectionDecoratorCommand implements DecoratorCommand {
    private final String message = "Some of printers are not available";
    protected Printer[] printers;

    /**
     * Format input string by passed format rules
     *
     * @param args array of string
     */
    public abstract void decorate(String... args) throws DecoratorException;

    /**
     * Flushs printers buffer if it is exists
     *
     * @throws DecoratorException
     */
    public void flush() throws DecoratorException {
        DecoratorException de = new DecoratorException(message);
        for (Printer printer : printers) {
            try {
                printer.flush();
            } catch (PrinterException e) {
                throw new DecoratorException("Printer is not available", e);
            }
        }

        if (!de.getPrinterExceptionsList().isEmpty()) {
            throw de;
        }
    }

    /**
     * Prints message on all available printers
     *
     * @param message param to print
     * @throws DecoratorException
     */
    protected void printToAllPrinters(String message) throws DecoratorException {
        DecoratorException de = new DecoratorException(this.message);
        for (Printer printer : printers) {
            try {
                printer.log(message);
            } catch (PrinterException e) {
                de.addPrinterException(e);
            }
        }

        if (!de.getPrinterExceptionsList().isEmpty()) {
            throw de;
        }
    }
}
