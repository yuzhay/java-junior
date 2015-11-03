package com.acme.edu;

/**
 * PostfixDecoratorCommand prints string with specified prefix
 * Created by Yuriy on 03.11.2015.
 */
public class PostfixDecoratorCommand implements DecoratorCommand {

    //region private fields
    private final Printer printer;
    private final String postfix;
    //endregion

    //region constructor

    /**
     * Creates instance of PrefixDecoratorCommand
     *
     * @param printer instance of Printer which will be used to log information
     */
    public PostfixDecoratorCommand(Printer printer, String postfix) {
        this.printer = printer;
        this.postfix = postfix;
    }
    //endregion


    //region public methods

    /**
     * Decorates input string by prefix
     *
     * @param args    array of string
     */
    @Override
    public void decorate(String... args) {
        String joinedStr = String.join(" ", args);
        printer.log(joinedStr + postfix);
    }
    //endregion
}
