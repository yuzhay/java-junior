package com.acme.edu.unit;

import com.acme.edu.*;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Mock test for Logger States and Decorators
 * Created by Yuriy on 03.11.2015.
 */
public class SimpleLoggerTest {
    private DecoratorCommand decor;
    private Printer printer;

    @Before
    public void init() {
        decor = mock(DecoratorCommand.class);
        printer = mock(SimplePrinter.class);
    }

    @Test
    public void shouldIntStateCallDecorateMethod() {
        IntState intState = new IntState();
        intState.log("10", decor);
        intState.log("10", decor);
        intState.log("13", decor);
        intState.flush(decor);

        verify(decor).decorate("33");
    }

    @Test
    public void shouldStringStateCallDecorateMethod() {
        StringState strState = new StringState();
        strState.log("str1", decor);
        strState.log("str2", decor);
        strState.log("str2", decor);
        strState.flush(decor);

        verify(decor).decorate("str1");
        verify(decor).decorate("str2 (x2)");
    }

    @Test
    public void shouldCharStateCallDecorateMethod() {
        CharState cState = new CharState();
        cState.log("1", decor);
        cState.log("3", decor);
        cState.log("2", decor);
        cState.flush(decor);

        verify(decor).decorate("1");
        verify(decor).decorate("3");
        verify(decor).decorate("2");
    }

    @Test
    public void shouldBoolStateCallDecorateMethod() {
        BoolState bState = new BoolState();
        bState.log("true", decor);
        bState.log("false", decor);
        bState.flush(decor);

        verify(decor).decorate("true");
        verify(decor).decorate("false");
    }

    @Test
    public void shouldPrefixDecoratorCallPrinterLogMethod() {
        PrefixDecoratorCommand dec = new PrefixDecoratorCommand(printer, "myprefix:");
        dec.decorate("HelloWorld");

        verify(printer).log("myprefix:HelloWorld");
    }

    @Test
    public void shouldPostfixDecoratorCallPrinterLogMethod() {
        PostfixDecoratorCommand dec = new PostfixDecoratorCommand(printer, "_mypostfix");
        dec.decorate("HelloThere");

        verify(printer).log("HelloThere_mypostfix");
    }

    @Test
    public void shouldFormatDecoratorCallPrinterLogMethod() {
        FormatDecoratorCommand dec = new FormatDecoratorCommand(printer, "(%s)-{%s}-[%s]");
        dec.decorate("a", "b", "c");

        verify(printer).log("(a)-{b}-[c]");
    }
}
