package com.acme.edu.decorators;

import com.acme.edu.printers.Printer;
import com.acme.edu.exceptions.DecoratorException;
import com.acme.edu.exceptions.PrinterException;

/**
 * PrefixDecoratorCommand prints string with specified prefix
 * Created by Yuriy on 03.11.2015.
 */
public class PrefixDecoratorCommand extends DependencyInjectionDecoratorCommand {

    //region private fields
    private final String prefix;
    //endregion

    //region constructor

    /**
     * Creates instance of PrefixDecoratorCommand
     *
     * @param printers instances of Printer which will be used to log information
     */
    public PrefixDecoratorCommand(String prefix, Printer... printers) throws DecoratorException {
        if (prefix == null) {
            throw new DecoratorException("PrefixDecorator format argument should not be null");
        }

        if (printers == null || printers.length == 0) {
            throw new DecoratorException("Constructor printer argument couldn't be null");
        }
        this.printers = printers;
        this.prefix = prefix;
    }
    //endregion


    //region public methods

    /**
     * Decorates input string by prefix
     *
     * @param args array of string
     */
    @Override
    public void decorate(String... args) throws DecoratorException {
        if (args == null || args.length == 0) {
            throw new DecoratorException("PrefixDecoder arguments are null or empty");
        }
        String joinedStr = String.join(" ", args);
        if (args.length > 0) {
            printToAllPrinters(prefix + joinedStr);
        } else {
            printToAllPrinters(prefix);
        }
    }

    //endregion
}
