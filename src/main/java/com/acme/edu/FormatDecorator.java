package com.acme.edu;

/**
 * FormatDecorator prints string using format
 * Created by Yuriy on 03.11.2015.
 */
public class FormatDecorator implements Decorator {

    //region private fields
    private final Printer printer;
    //endregion

    //region constructor

    /**
     * Creates instance of PrefixDecorator
     *
     * @param printer instance of Printer which will be used to log information
     */
    public FormatDecorator(Printer printer) {
        this.printer = printer;
    }
    //endregion


    //region public methods

    /**
     * Format input string by passed format rules
     *
     * @param format will be appended to string
     * @param args   array of string
     */
    @Override
    public void decorate(String format, String... args) {
        printer.log(String.format(format, args));
    }
    //endregion
}
