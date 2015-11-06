package com.acme.edu.decorators;

import com.acme.edu.exceptions.DecoratorException;
import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.printers.Printer;

/**
 * Created by Yuriy on 06.11.2015.
 */
public abstract class DependencyInjectionDecoratorCommand implements DecoratorCommand {
    protected Printer[] printers;

    public abstract void decorate(String... args) throws DecoratorException;

    protected void printToAllPrinters(String message) throws DecoratorException {
        for (Printer printer : printers) {
            try {
                printer.log(message);
            } catch (PrinterException e) {
                throw new DecoratorException("Printer is not available", e);
            }
        }
    }

    public void flush() throws DecoratorException {
        for (Printer printer : printers) {
            try {
                printer.flush();
            } catch (PrinterException e) {
                throw new DecoratorException("Printer is not available", e);
            }
        }
    }
}
