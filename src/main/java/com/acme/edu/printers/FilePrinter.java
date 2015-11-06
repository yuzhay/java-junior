package com.acme.edu.printers;

import com.acme.edu.exceptions.PrinterException;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by Yuriy on 06.11.2015.
 */
public class FilePrinter implements Printer {
    private Charset charset = Charset.forName("utf-8");
    private String file;
    private StringBuilder buffer = new StringBuilder();
    private int bufSize = 50;
    private int counter = 0;

    /**
     * Creates FilePrinter
     *
     * @param file output file
     * @throws PrinterException
     */
    public FilePrinter(String file) throws PrinterException {
        this.file = file;
    }

    /**
     * Creates FilePrinter
     * @param file  output file
     * @param charset encoding to use
     * @throws PrinterException
     */
    public FilePrinter(String file, Charset charset) throws PrinterException {
        this.file = file;
        this.charset = charset;
    }

    /**
     * Creates FilePrinter
     * @param file  output file
     * @param charset encoding to use
     * @param bufSize max buffer message count
     * @throws PrinterException
     */
    public FilePrinter(String file, Charset charset, int bufSize) throws PrinterException {
        this.file = file;
        this.charset = charset;
        this.bufSize = bufSize;
    }

    /**
     * Logs message to output file
     * @param message print parameter
     * @throws PrinterException
     */
    @Override
    public void log(String message) throws PrinterException {
        buffer.append(message).append(System.lineSeparator());
        counter++;
        if (counter >= bufSize) {
            writeToFile(buffer.toString());
            buffer = new StringBuilder();
            counter = 0;
        }
    }

    /**
     * Flushes buffer
     * @throws PrinterException
     */
    @Override
    public void flush() throws PrinterException {
        writeToFile(buffer.toString());
        buffer = new StringBuilder();
    }


    private void writeToFile(String message) throws PrinterException {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, charset)) {
            outputStreamWriter.write(message);
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new PrinterException("Can't write to file ", e);
        }
    }

}
