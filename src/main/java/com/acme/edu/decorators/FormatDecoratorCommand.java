package com.acme.edu.decorators;

import com.acme.edu.printers.Printer;
import com.acme.edu.exceptions.DecoratorException;
import com.acme.edu.exceptions.PrinterException;

/**
 * FormatDecoratorCommand prints string using format
 * Created by Yuriy on 03.11.2015.
 */
public class FormatDecoratorCommand extends DependencyInjectionDecoratorCommand {

    //region private fields
    private final String format;
    //endregion

    //region constructor

    /**
     * Creates instance of PrefixDecoratorCommand
     *
     * @param printers instances of Printers which will be used to log information
     */
    public FormatDecoratorCommand(String format, Printer... printers) throws DecoratorException {
        if (format == null) {
            throw new DecoratorException("FormatDecorator: format should not be null");
        }

        if (printers == null || printers.length == 0) {
            throw new DecoratorException("Constructor printer argument couldn't be null");
        }

        this.printers = printers;
        this.format = format;
    }

    //endregion


    //region public methods

    /**
     * Format input string by passed format rules
     *
     * @param args array of string
     */
    @Override
    public void decorate(String... args) throws DecoratorException {
        if (args == null || args.length == 0) {
            throw new DecoratorException("FormatDecoder arguments are null or empty");
        }
        printToAllPrinters(String.format(format, args));
    }
    //endregion
}
