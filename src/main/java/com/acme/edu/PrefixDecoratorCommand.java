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
    public PrefixDecoratorCommand(Printer printer, String prefix) {
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
    public void decorate(String... args) {
        String joinedStr = String.join(" ", args);
        if (args.length > 0) {
            printer.log(prefix + joinedStr);
        } else {
            printer.log(prefix);
        }
    }
    //endregion
}
