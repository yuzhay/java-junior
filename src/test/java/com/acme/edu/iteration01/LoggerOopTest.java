package com.acme.edu.iteration01;

import com.acme.edu.Logger;
import com.acme.edu.SimpleLogger;
import com.acme.edu.SimplePrinter;
import com.acme.edu.SysoutCaptureAndAssertionAbility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LoggerOopTest implements SysoutCaptureAndAssertionAbility {
    //region given
    private SimpleLogger sp;


    @Before
    public void setUpSystemOut() throws IOException {
        tearDown();
        captureSysout();

        sp = new SimpleLogger(new SimplePrinter());
    }
    //endregion

    @After
    public void tearDown() {
        resetOut();
    }

    @Test
    public void shouldLogInteger() throws IOException {
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

    /*@Test
    public void shouldLogByte() throws IOException {
        //region when
        Logger.log((byte) 1);
        Logger.log((byte) 0);
        Logger.log((byte) -1);
        Logger.close();
        //endregion

        //region then
        assertSysoutContains("primitive: ");
        assertSysoutContains("0");
        //endregion
    }*/

    @Test
    public void shouldLogChar() throws IOException {
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
    public void shouldLogString() throws IOException {
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
    public void shouldLogBoolean() throws IOException {
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

    /*@Test
    public void shouldLogReference() throws IOException {
        //region when
        Logger.log(new Object());
        Logger.close();
        //endregion

        //region then
        assertSysoutContains("reference: ");
        assertSysoutContains("@");
        //endregion
    }*/


}