package com.acme.edu.loggers;

import com.acme.edu.exceptions.LoggerException;
import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.printers.FilePrinter;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Yuriy on 06.11.2015.
 */
public class FilePrinterLoggerServer {
    //region private fields
    private ServerSocket socket;
    private Logger logger;
    //endregion

    /**
     * Creates FilePrinterLoggerServer
     *
     * @param port     Server will be waiting for clients on mentioned port
     * @param fileName Output log filename
     * @throws LoggerException
     */
    public FilePrinterLoggerServer(int port, String fileName) throws LoggerException {
        try {
            socket = new ServerSocket(port);
            logger = new Logger(new FilePrinter(fileName));
        } catch (IOException e) {
            throw new LoggerException("Server couldn't start", e);
        } catch (PrinterException e) {
            throw new LoggerException("Can't create server logger", e);
        }
    }

    /**
     * Starts FilePrinterLogger server.
     *
     * @throws LoggerException
     */
    public void start() throws LoggerException {
        /*
        todo: rewrite code using threads
         */
        Socket client;
        DataInputStream dis;
        try {
            client = socket.accept();
            dis = new DataInputStream(client.getInputStream());
            String buffer = dis.readUTF();
            JSONObject jsonResponse = getJsonResponse(buffer);
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(jsonResponse.toString());
            dos.flush();
        } catch (IOException e) {
            throw new LoggerException("Server can't read from socket", e);
        }
    }

    //region private methods
    private JSONObject getJsonResponse(String buffer) throws LoggerException {
        JSONObject jsonRequest = new JSONObject(buffer);

        if (!jsonRequest.keySet().contains("message")) {
            throw new LoggerException("Server incorrect json message");
        }

        Object obj = jsonRequest.get("message");
        if (!(obj instanceof String)) {
            throw new LoggerException("Unknown command");
        }

        String msg = (String) obj;
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "ok");
        jsonResponse.put("hash", msg.hashCode());

        try {
            logger.log(msg, false);
            logger.close();
        } catch (LoggerException ex) {
            jsonResponse.put("status", "error");
            jsonResponse.put("error", ex.toString());
        }
        return jsonResponse;
    }

    //endregion
}
