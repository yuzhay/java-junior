package com.acme.edu.printers;

import com.acme.edu.exceptions.PrinterException;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Yuriy on 06.11.2015.
 */
public class NetPrinter implements Printer {
    private Socket client;
    private String host;
    private int port;

    public NetPrinter(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void log(String message) throws PrinterException {
        try {
            client = new Socket(host, port);
        } catch (IOException e) {
            throw new PrinterException("Server is not available", e);
        }

        try (
                OutputStream os = client.getOutputStream();
                DataOutputStream das = new DataOutputStream(os)
        ) {
            das.writeUTF(message);
        } catch (IOException e) {
            throw new PrinterException("Data not sent", e);
        }
    }

    @Override
    public void flush() throws PrinterException {

    }
}
