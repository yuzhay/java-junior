package com.acme.edu.iteration03;

import com.acme.edu.*;
import com.acme.edu.exceptions.LoggerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LoggerOopTest implements SysoutCaptureAndAssertionAbility {
    private SimpleLogger sp;

    //region given
    @Before
    public void setUpSystemOut() throws IOException {
        tearDown();
        captureSysout();

        sp = new SimpleLogger();
    }
    //endregion

    @After
    public void tearDown() {
        resetOut();
    }

    @Test
    public void shouldLogIntegersArray() throws LoggerException {
        //region when
        sp.log(new int[]{-1, 0, 1});
        //endregion

        //region then
        assertSysoutEquals("primitive: 0" + System.lineSeparator());
        //endregion
    }

    @Test
    public void shouldLogIntegersMatrix() throws LoggerException {
        //region when
        sp.log(new int[][]{{-1, 0, 1}, {1, 2, 3}, {-1, -2, -3}});
        //endregion

        //region then
        assertSysoutEquals(
                "primitive: 0" + System.lineSeparator()
        );
        //endregion
    }

    @Test
    public void shouldLogIntegersMulitidimentionalArray() throws LoggerException {

        String sep = System.lineSeparator();
        //region when
        sp.log(new int[][][][]{{{{0}}}});
        //endregion

        //region then
        assertSysoutEquals(
                "primitive: 0" + sep
        );
        //endregion
    }

    @Test
    public void shouldLogStringsWithOneMethodCall() throws LoggerException {
        String sep = System.lineSeparator();

        //region when
        sp.log("str1", "string 2", "str 3");
        //endregion

        //region then
        assertSysoutContains("string: str1" + sep + "string: string 2" + sep + "string: str 3");
        //endregion
    }

    @Test
    public void shouldLogIntegersWithOneMethodCall() throws LoggerException {
        //region when
        sp.log(-1, 0, 1, 3);
        //endregion

        //region then
        assertSysoutContains("primitive: 3");
        //endregion
    }

    @Test
    public void shouldCorrectDealWithIntegerOverflowWhenOneMethodCall() throws LoggerException {
        //region when
        sp.log(1);
        sp.log("str");
        sp.log(Integer.MAX_VALUE - 10);
        sp.log(11);
        sp.close();
        //endregion

        //region then
        assertSysoutContains("primitive: 1");
        assertSysoutContains("string: str");
        assertSysoutContains("primitive: " + (Integer.MAX_VALUE - 10));
        assertSysoutContains("primitive: 11");
        //endregion
    }
}