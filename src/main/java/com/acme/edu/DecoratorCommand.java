package com.acme.edu;

/**
 * Created by Yuriy on 03.11.2015.
 */
public interface DecoratorCommand {
    void decorate(String... args) throws PrinterException, DecoratorException;
}
