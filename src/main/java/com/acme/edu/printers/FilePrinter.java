package com.acme.edu.printers;

import com.acme.edu.exceptions.PrinterException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuriy on 06.11.2015.
 */
public class FilePrinter implements Printer {
    private Charset charset = Charset.forName("utf-8");
    private String file;
    private int bufSize = 50;
    private List<String> bufferList = new ArrayList<>(bufSize);

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
     *
     * @param file    output file
     * @param charset encoding to use
     * @throws PrinterException
     */
    public FilePrinter(String file, Charset charset) throws PrinterException {
        this(file);
        this.charset = charset;
    }

    /**
     * Creates FilePrinter
     *
     * @param file    output file
     * @param charset encoding to use
     * @param bufSize max buffer message count
     * @throws PrinterException
     */
    public FilePrinter(String file, Charset charset, int bufSize) throws PrinterException {
        this(file, charset);
        this.bufSize = bufSize;
        this.bufferList = new ArrayList<>(bufSize);
    }

    /**
     * Logs message to output file
     *
     * @param message print parameter
     * @throws PrinterException
     */
    @Override
    public void log(String message) throws PrinterException {
        bufferList.add(message);
        if (bufferList.size() >= bufSize) {
            sort();
            writeToFile(joinBufferListToString());
            bufferList.clear();
        }
    }

    /**
     * Flushes buffer
     *
     * @throws PrinterException
     */
    @Override
    public void flush() throws PrinterException {
        sort();
        writeToFile(joinBufferListToString());
        bufferList.clear();
    }

    private void sort() {
        bufferList.sort((o1, o2) -> {
            boolean o1ContainsError = o1.contains("ERROR");
            if (o1ContainsError && o2.contains("ERROR")) {
                return 0;
            } else if (o1ContainsError) {
                return -1;
            } else {
                return 1;
            }
        });
    }

    private String joinBufferListToString() {
        return bufferList.stream().reduce((t, u) -> t + System.lineSeparator() + u).get();
    }

    private void writeToFile(String message) throws PrinterException {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, charset)) {
            outputStreamWriter.write(message + System.lineSeparator());
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new PrinterException("Can't write to file ", e);
        }
    }

}
