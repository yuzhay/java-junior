package com.acme.edu.decorators;

import com.acme.edu.exceptions.DecoratorException;

/**
 * Created by Yuriy on 03.11.2015.
 */
public interface DecoratorCommand {
    void decorate(String... args) throws DecoratorException;
}
