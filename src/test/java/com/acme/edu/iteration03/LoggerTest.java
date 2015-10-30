package com.acme.edu.iteration03;

import com.acme.edu.Logger;
import com.acme.edu.SysoutCaptureAndAssertionAbility;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class LoggerTest implements SysoutCaptureAndAssertionAbility {
    //region given
    @Before
    public void setUpSystemOut() throws IOException {
        tearDown();
        captureSysout();
    }
    //endregion

    @After
    public void tearDown() {
        resetOut();
    }

    @Test
    public void shouldLogIntegersArray() throws IOException {
        //region when
        Logger.log(new int[]{-1, 0, 1});
        //endregion

        //region then
        assertSysoutEquals(
                "primitives array: {-1, 0, 1}" + System.lineSeparator()
        );
        //endregion
    }

    @Test
    public void shouldLogIntegersMatrix() throws IOException {
        //region when
        Logger.log(new int[][]{{-1, 0, 1}, {1, 2, 3}, {-1, -2, -3}});
        //endregion

        //region then
        assertSysoutEquals(
                "primitives matrix: {" + System.lineSeparator() +
                        "{-1, 0, 1}" + System.lineSeparator() +
                        "{1, 2, 3}" + System.lineSeparator() +
                        "{-1, -2, -3}" + System.lineSeparator() +
                        "}" + System.lineSeparator()
        );
        //endregion
    }

    @Test
    public void shouldLogIntegersMulitidimentionalArray() throws IOException {

        String sep = System.lineSeparator();
        //region when
        Logger.log(new int[][][][]{{{{0}}}});
        //endregion

        //region then
        assertSysoutEquals(
                "primitives multimatrix: {" + sep +
                        "{" + sep + "{" + sep + "{" + sep +
                        "0" + sep +
                        "}" + sep + "}" + sep + "}" + sep +
                        "}" + sep
        );
        //endregion
    }

    @Test
    public void shouldLogStringsWithOneMethodCall() throws IOException {
        String sep = System.lineSeparator();

        //region when
        Logger.log("str1", "string 2", "str 3");
        //endregion

        //region then
        assertSysoutContains("str1" + sep + "string 2" + sep + "str 3");
        //endregion
    }

    @Test
    @Ignore
    public void shouldLogIntegersWithOneMethodCall() throws IOException {
        //region when
        Logger.log(-1, 0, 1, 3);
        //endregion

        //region then
        assertSysoutContains("primitive: 3");
        //endregion
    }

    @Test
    public void shouldCorrectDealWithIntegerOverflowWhenOneMethodCall() throws IOException {
        //region when
        Logger.log(1);
        Logger.log("str");
        Logger.log(Integer.MAX_VALUE - 10);
        Logger.log(11);
        Logger.close();
        //endregion

        //region then
        assertSysoutContains("primitive: 1");
        assertSysoutContains("string: str");
        assertSysoutContains("primitive: " + (Integer.MAX_VALUE - 10));
        assertSysoutContains("primitive: 11");
        //endregion
    }
}