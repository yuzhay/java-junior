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
import java.net.SocketTimeoutException;

/**
 * Created by Yuriy on 06.11.2015.
 * Network version of Printer class which is used in Logger class.
 */
public class FilePrinterLoggerServer {
    //region private fields
    private ServerSocket socket;
    private Logger logger;
    private Thread serverThread;

    private class Server implements Runnable {
        @Override
        public void run() {
            while (serverThread == Thread.currentThread()) {
                try (Socket client = socket.accept();
                     DataInputStream dis = new DataInputStream(client.getInputStream());
                     DataOutputStream dos = new DataOutputStream(client.getOutputStream())
                ) {
                    JSONObject jsonResponse = getJsonResponse(dis.readUTF());
                    dos.writeUTF(jsonResponse.toString());
                    dos.flush();
                } catch (SocketTimeoutException ste) {
                    /*Do nothing. Time is out. Wait for next client*/
                    ste.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException("FilePrinterLoggerServer IOExceptions", e);
                }
            }
            Thread.currentThread().interrupt();
        }
    }

    private Server serverRunner = new Server();
    //endregion

    /**
     * Creates FilePrinterLoggerServer
     *
     * @param port     Server will be waiting for clients on mentioned port
     * @param fileName Output log filename
     * @param socketTimeout  timeout in milliseconds, used in socket.setSoTimeout()
     * @throws LoggerException
     */
    public FilePrinterLoggerServer(int port, String fileName, int socketTimeout) throws LoggerException {
        try {
            socket = new ServerSocket(port);
            socket.setSoTimeout(socketTimeout);
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
        try {
            serverThread = new Thread(serverRunner);
            serverThread.start();
        } catch (RuntimeException ex) {
            throw new LoggerException("Message ", ex);
        }
    }

    /**
     * Stop server thread
     */
    public void stop() {
        serverThread = null;
    }

    //region private methods
    private JSONObject getJsonResponse(String buffer) {
        JSONObject jsonRequest = new JSONObject(buffer);
        JSONObject jsonResponse = new JSONObject();

        if (!jsonRequest.keySet().contains("message")) {
            jsonResponse.put("status", "error");
            jsonResponse.put("error", "unknown request");
            return jsonResponse;
        }

        Object obj = jsonRequest.get("message");

        String msg = (String) obj;
        jsonResponse.put("status", "ok");
        jsonResponse.put("hash", msg.hashCode());

        try {
            logger.log(msg, false);
            logger.close();
        } catch (LoggerException ex) {
            jsonResponse.put("status", "error");
            jsonResponse.put("error", ex.toString());
            ex.printStackTrace();
        }
        return jsonResponse;
    }
    //endregion
}
