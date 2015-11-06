package com.acme.edu.iteration04;

import com.acme.edu.loggers.Logger;
import com.acme.edu.exceptions.LoggerException;
import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.printers.FilePrinter;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yuriy on 06.11.2015.
 */
public class LoggerOopTest {

    //region given
    @Before
    public void setUpSystemOut() throws IOException {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void shouldPrintToLogFile() throws PrinterException, LoggerException, IOException {
        //region when
        Charset charset = Charset.forName("utf-8");
        String fileName = "FilePrinterOutputFile.txt";
        String message = "Hello world";
        String expected = "string: " + message + System.lineSeparator() + "primitive: 15" + System.lineSeparator();
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }

        Logger sp = new Logger(
                new FilePrinter(fileName, charset));
        sp.log(message);
        sp.log(5);
        sp.log(10);
        sp.close();
        //endregion

        //region then
        String actual = FileUtils.readFileToString(file, charset);
        assertEquals(expected, actual);
        file.delete();
        //endregion
    }
}
