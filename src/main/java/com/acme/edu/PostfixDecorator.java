package com.acme.edu;

/**
 * PostfixDecorator prints string with specified prefix
 * Created by Yuriy on 03.11.2015.
 */
public class PostfixDecorator implements Decorator {

    //region private fields
    private final Printer printer;
    //endregion

    //region constructor

    /**
     * Creates instance of PrefixDecorator
     *
     * @param printer instance of Printer which will be used to log information
     */
    public PostfixDecorator(Printer printer) {
        this.printer = printer;
    }
    //endregion


    //region public methods

    /**
     * Decorates input string by prefix
     *
     * @param postfix prefix
     * @param args    array of string
     */
    @Override
    public void decorate(String postfix, String... args) {
        String joinedStr = String.join(" ", args);
        printer.log(joinedStr + postfix);
    }
    //endregion
}
