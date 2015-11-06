package com.acme.edu.printers;

import com.acme.edu.exceptions.PrinterException;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Yuriy on 06.11.2015.
 */
public class FilePrinter implements Printer {
    private String file;

    public FilePrinter(String file) throws PrinterException {
        this.file = file;
    }

    @Override
    public void log(String message) throws PrinterException {
        try (
                RandomAccessFile fout = new RandomAccessFile(file, "w")) {
            fout.writeUTF(message);
        } catch (IOException e) {
            throw new PrinterException("Can't write to output file", e);
        }
    }
}
