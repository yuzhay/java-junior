package com.acme.edu.decorators;

import com.acme.edu.printers.Printer;
import com.acme.edu.exceptions.DecoratorException;

/**
 * PostfixDecoratorCommand prints string with specified prefix
 * Created by Yuriy on 03.11.2015.
 */
public class PostfixDecoratorCommand extends DependencyInjectionDecoratorCommand {

    //region private fields
    private final String postfix;
    //endregion

    //region constructor

    /**
     * Creates instance of PrefixDecoratorCommand
     *
     * @param printers instances of Printer which will be used to log information
     */
    public PostfixDecoratorCommand(String postfix, Printer... printers) throws DecoratorException {
        if (postfix == null) {
            throw new DecoratorException("PostfixDecorator format argument should not be null");
        }

        if (printers == null || printers.length == 0) {
            throw new DecoratorException("Constructor printer argument couldn't be null");
        }

        this.printers = printers;
        this.postfix = postfix;
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
            throw new DecoratorException("PostfixDecorator arguments are null or empty");
        }
        String joinedStr = String.join(" ", args);
        printToAllPrinters(joinedStr + postfix);
    }
    //endregion
}
