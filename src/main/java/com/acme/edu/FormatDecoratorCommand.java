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
    public FormatDecoratorCommand(Printer printer, String format) {
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
    public void decorate(String... args) {
        printer.log(String.format(format, args));
    }
    //endregion
}
