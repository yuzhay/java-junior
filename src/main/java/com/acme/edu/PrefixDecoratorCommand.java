package com.acme.edu;

/**
 * PrefixDecoratorCommand prints string with specified prefix
 * Created by Yuriy on 03.11.2015.
 */
public class PrefixDecoratorCommand implements DecoratorCommand {

    //region private fields
    private final Printer printer;
    private final String prefix;
    //endregion

    //region constructor

    /**
     * Creates instance of PrefixDecoratorCommand
     *
     * @param printer instance of Printer which will be used to log information
     */
    public PrefixDecoratorCommand(Printer printer, String prefix) throws DecoratorException, PrinterException {
        if (prefix == null) {
            throw new DecoratorException("PrefixDecorator format argument should not be null");
        }

        if (printer == null) {
            throw new DecoratorException("Constructor printer argument couldn't be null");
        }
        this.printer = printer;
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
        try {
            String joinedStr = String.join(" ", args);
            if (args.length > 0) {
                printer.log(prefix + joinedStr);
            } else {
                printer.log(prefix);
            }
        } catch (PrinterException ex) {
            throw new DecoratorException("Printer error", ex);
        }
    }
    //endregion
}
