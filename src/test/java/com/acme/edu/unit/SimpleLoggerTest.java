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
        intState.log("-1", decor);
        intState.log("0", decor);
        intState.log("1", decor);
        intState.flush();

        verify(decor).decorate("0");
    }

    @Test
    public void shouldStringStateCallDecorateMethod() {
        StringState strState = new StringState();
        strState.log("ThisStringShouldBePrintedOnce", decor);
        strState.log("ThisStringShouldBePrintedTwice", decor);
        strState.log("ThisStringShouldBePrintedTwice", decor);
        strState.flush();

        verify(decor).decorate("ThisStringShouldBePrintedOnce");
        verify(decor).decorate("ThisStringShouldBePrintedTwice (x2)");
    }

    @Test
    public void shouldCharStateCallDecorateMethod() {
        CharState cState = new CharState();
        cState.log("1", decor);
        cState.log("3", decor);
        cState.log("2", decor);
        cState.flush();

        verify(decor).decorate("1");
        verify(decor).decorate("3");
        verify(decor).decorate("2");
    }

    @Test
    public void shouldBoolStateCallDecorateMethod() {
        BoolState bState = new BoolState();
        bState.log("true", decor);
        bState.log("false", decor);
        bState.flush();

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
