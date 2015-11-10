package com.acme.edu.printers;

import com.acme.edu.exceptions.PrinterException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by Yuriy on 06.11.2015.
 */
public class NetPrinter implements Printer {

    //region private fields
    private final Charset charset = Charset.forName("utf-8");
    private Socket client;
    private String host;
    private int port;
    //endregion

    /**
     * Creates Printer client. Log messages will be sent to host:port.
     *
     * @param host domain name or IP
     * @param port any port 1024..65535
     */
    public NetPrinter(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Sends log message to remote server
     *
     * @param message print parameter
     * @throws PrinterException
     */
    @Override
    public void log(String message) throws PrinterException {
        try {
            client = new Socket(host, port);
        } catch (IOException e) {
            throw new PrinterException("Server is not available", e);
        }

        try (
                OutputStream os = client.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, charset)
        ) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", message);

            osw.write(jsonObject.toString());
            osw.flush();
        } catch (IOException e) {
            throw new PrinterException("Data not sent", e);
        }
    }

    /**
     * Flushes buffer
     *
     * @throws PrinterException
     */
    @Override
    public void flush() throws PrinterException {
        /*Implement when NetPrinter will work with buffer*/
    }
}
