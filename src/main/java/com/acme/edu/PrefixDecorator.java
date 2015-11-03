package com.acme.edu;

/**
 * PrefixDecorator prints string with specified prefix
 * Created by Yuriy on 03.11.2015.
 */
public class PrefixDecorator implements Decorator {

    //region private fields
    private final Printer printer;
    //endregion

    //region constructor

    /**
     * Creates instance of PrefixDecorator
     *
     * @param printer instance of Printer which will be used to log information
     */
    public PrefixDecorator(Printer printer) {
        this.printer = printer;
    }
    //endregion


    //region public methods

    /**
     * Decorates input string by prefix
     *
     * @param prefix will be appended to string
     * @param args   array of string
     */
    @Override
    public void decorate(String prefix, String... args) {
        String joinedStr = String.join(" ", args);
        printer.log(prefix + joinedStr);
    }
    //endregion
}
