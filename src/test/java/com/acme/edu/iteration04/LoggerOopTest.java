package com.acme.edu.iteration04;

import com.acme.edu.loggers.FilePrinterLoggerServer;
import com.acme.edu.loggers.Logger;
import com.acme.edu.exceptions.LoggerException;
import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.printers.FilePrinter;
import com.acme.edu.printers.NetPrinter;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
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
    private String fileName = "LoggerNetOutputFile.txt";
    private File file = new File(fileName);
    private String host = "127.0.0.1";
    private int port = 12345;
    private Charset charset = Charset.forName("utf-8");

    //region given
    @Before
    public void setUpSystemOut() throws IOException {
        if (file.exists()) {
            file.delete();
        }
    }

    @After
    public void tearDown() {
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void shouldLoggerPrintToLogFile() throws PrinterException, LoggerException, IOException {
        //region when
        String message = "Hello world";
        String expected = "string: " + message + System.lineSeparator() + "primitive: 15" + System.lineSeparator();

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
        //endregion
    }

    @Test
    public void shouldLogNetPrinter() throws LoggerException, IOException, InterruptedException {
        //region when
        String expected = "primitive: 8" + System.lineSeparator();
        String fileName = "LoggerNetOutputFile.txt";

        FilePrinterLoggerServer fpls = new FilePrinterLoggerServer(port, fileName);

        Thread t1 = new Thread(() -> {
            try {
                fpls.start();
            } catch (LoggerException e) {
                e.printStackTrace();
            }

        });
        t1.start();

        Logger logger = new Logger(new NetPrinter(host, port));
        logger.log(5);
        logger.log(3);
        logger.close();
        //endregion

        //region then
        //Wait until file will be created
        Thread.sleep(1000);
        String actual = FileUtils.readFileToString(file, charset);
        assertEquals(expected, actual);
        //endregion
    }

    @Test(expected = LoggerException.class)
    public void shouldThrowLoggerException() throws LoggerException, IOException {
        //region when
        String message = "HelloServerString";
        FilePrinterLoggerServer fpls = new FilePrinterLoggerServer(port, fileName);

        Thread t1 = new Thread(() -> {
            try {
                fpls.start();
            } catch (LoggerException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        //endregion

        //region then
        Logger logger = new Logger(new NetPrinter(host + "a", port));
        logger.log((String) null);
        logger.close();
        //endregion
    }
}
