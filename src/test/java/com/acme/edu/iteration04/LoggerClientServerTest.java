package com.acme.edu.iteration04;

import com.acme.edu.loggers.Logger;
import com.acme.edu.exceptions.LoggerException;
import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.loggers.LoggerServer;
import com.acme.edu.printers.FilePrinter;
import com.acme.edu.printers.NetPrinter;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yuriy on 06.11.2015.
 * This test covers client-server implementation of Logger class.
 */
public class LoggerClientServerTest {
    private final String fileName = "LoggerNetOutputFile.txt";
    private final File file = new File(fileName);
    private final String host = "127.0.0.1";
    private final Charset charset = Charset.forName("utf-8");
    private final int serverTimeout = 1000;
    private int port = 32350;
    private LoggerServer server;
    private Random portRnd = new Random();

    //region given
    @Before
    public void setUpSystemOut() throws IOException, LoggerException, InterruptedException {
        port = portRnd.nextInt(64000) + 1024;
        server = new LoggerServer(port, fileName, serverTimeout);
        server.start();
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }

    @After
    public void tearDown() throws InterruptedException {
        if (server != null) {
            server.stop();
        }
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }

        //Wait until server stops
        Thread.sleep(2 * serverTimeout);
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
    public void shouldLoggerPrintPriorityToLogFile() throws PrinterException, LoggerException, IOException {
        //region when
        String message1 = "Low priority message";
        String message2 = "ERROR: This magic high priority string";

        String expected = "string: " + message2 + System.lineSeparator() +
                "string: " + message1 + System.lineSeparator();

        Logger sp = new Logger(
                new FilePrinter(fileName, charset));
        sp.log(message1);
        sp.log(message2);

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
        Logger logger = new Logger(new NetPrinter(host, port));
        logger.log(5);
        logger.log(3);
        logger.close();
        //endregion

        //region then
        //Wait until file will be created
        Thread.sleep(2000);
        String actual = FileUtils.readFileToString(file, charset);
        assertEquals(expected, actual);
        //endregion
    }

    @Test(expected = LoggerException.class)
    public void shouldThrowLoggerException() throws LoggerException, IOException {
        Logger logger = new Logger(new NetPrinter(host + "error in host", port));
        logger.log((String) null);
        logger.close();
    }
}
