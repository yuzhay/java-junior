package com.acme.edu;

/**
 * FormatDecoratorCommand prints string using format
 * Created by Yuriy on 03.11.2015.
 */
public class FormatDecoratorCommand implements DecoratorCommand {

    //region private fields
    private final Printer printer;
    private final String format;
    //endregion

    //region constructor

    /**
     * Creates instance of PrefixDecoratorCommand
     *
     * @param printer instance of Printer which will be used to log information
     */
    public FormatDecoratorCommand(Printer printer, String format) throws DecoratorException {
        if (format == null) {
            throw new DecoratorException("FormatDecorator: format should not be null");
        }

        if (printer == null) {
            throw new DecoratorException("Constructor printer argument couldn't be null");
        }

        this.printer = printer;
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
        try {
            printer.log(String.format(format, args));
        } catch (PrinterException ex) {
            throw new DecoratorException("Printer error", ex);
        }
    }
    //endregion
}
