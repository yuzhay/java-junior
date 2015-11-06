package com.acme.edu.iteration01;

import com.acme.edu.*;
import com.acme.edu.exceptions.LoggerException;
import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.loggers.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoggerOopTest implements SysoutCaptureAndAssertionAbility {
    //region given
    private Logger sp;


    @Before
    public void setUpSystemOut() throws PrinterException {
        tearDown();
        captureSysout();

        sp = new Logger();
    }
    //endregion

    @After
    public void tearDown() {
        resetOut();
    }

    @Test
    public void shouldLogInteger() throws LoggerException {
        //region when

        sp.log(0);
        sp.log(-1);
        sp.log(1);
        sp.close();
        //endregion

        //region then
        assertSysoutContains("primitive: ");
        assertSysoutEquals("primitive: 0" + System.lineSeparator());
        //endregion
    }

    @Test
    public void shouldLogChar() throws LoggerException {
        //region when
        sp.log('a');
        sp.log('b');
        sp.close();
        //endregion

        //region then
        assertSysoutContains("char: ");
        assertSysoutContains("a");
        assertSysoutContains("b");
        //endregion
    }


    @Test
    public void shouldLogString() throws LoggerException {
        //region when
        sp.log("test string 1");
        sp.log("other str");
        sp.close();
        //endregion

        //region then
        assertSysoutContains("string: ");
        assertSysoutContains("test string 1");
        assertSysoutContains("other str");
        //endregion
    }

    @Test
    public void shouldLogBoolean() throws LoggerException {
        //region when
        sp.log(true);
        sp.log(false);
        sp.close();
        //endregion

        //region then
        assertSysoutContains("primitive: ");
        assertSysoutContains("true");
        assertSysoutContains("false");
        //endregion
    }
}