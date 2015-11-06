package com.acme.edu.iteration02;

import com.acme.edu.*;
import com.acme.edu.exceptions.LoggerException;
import com.acme.edu.loggers.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LoggerOopTest implements SysoutCaptureAndAssertionAbility {
    //region given

    private Logger sp;

    @Before
    public void setUpSystemOut() throws IOException {
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
    public void shouldLogSequentIntegersAsSum() throws LoggerException {
        //region when
        sp.log("str 1");
        sp.log(1);
        sp.log(2);
        sp.log("str 2");
        sp.log(0);
        sp.close();
        //endregion

        //region then
        assertSysoutEquals(
                "string: str 1" + System.lineSeparator() +
                        "primitive: 3" + System.lineSeparator() +
                        "string: str 2" + System.lineSeparator() +
                        "primitive: 0" + System.lineSeparator()
        );
        //endregion
    }

    @Test
    public void shouldLogCorrectlyIntegerOverflowWhenSequentIntegers() throws LoggerException {
        //region when
        sp.log("str 1");
        sp.log(10);
        sp.log(Integer.MAX_VALUE);
        sp.log("str 2");
        sp.log(0);
        sp.close();
        //endregion

        //region then
        assertSysoutEquals(
                "string: str 1" + System.lineSeparator() +
                        "primitive: 10" + System.lineSeparator() +
                        "primitive: " + Integer.MAX_VALUE + System.lineSeparator() +
                        "string: str 2" + System.lineSeparator() +
                        "primitive: 0" + System.lineSeparator()
        );
        //endregion
    }

    @Test
    public void shouldLogSameSubsequentStringsWithoutRepeat() throws LoggerException {
        //region when
        sp.log("str 1");
        sp.log("str 2");
        sp.log("str 2");
        sp.log(0);
        sp.log("str 2");
        sp.log("str 3");
        sp.log("str 3");
        sp.log("str 3");
        sp.close();
        //endregion

        //region then
        assertSysoutEquals(
                "string: str 1" + System.lineSeparator() +
                        "string: str 2 (x2)" + System.lineSeparator() +
                        "primitive: 0" + System.lineSeparator() +
                        "string: str 2" + System.lineSeparator() +
                        "string: str 3 (x3)" + System.lineSeparator()
        );
        //endregion
    }
}