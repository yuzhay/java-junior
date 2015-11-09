package com.acme.edu.loggers;

import com.acme.edu.exceptions.FilePrinterLoggerServerException;
import com.acme.edu.exceptions.LoggerException;
import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.printers.FilePrinter;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

/**
 * Created by Yuriy on 06.11.2015.
 * Network version of Printer class which is used in Logger class.
 */
public class FilePrinterLoggerServer {
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_HASH = "hash";
    public static final String FIELD_ERROR = "error";

    //region private fields
    private final Charset charset = Charset.forName("utf-8");
    private ServerSocket socket;
    private Logger logger;
    private Thread serverThread;
    private boolean isRuning = false;

    private class Server implements Runnable {
        @Override
        public void run() {

            while (serverThread == Thread.currentThread()) {
                try (Socket client = socket.accept();
                     BufferedReader br = new BufferedReader(
                             new InputStreamReader(client.getInputStream(), charset));
                     OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream(), charset)
                ) {
                    String line;
                    StringBuilder buffer = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        buffer.append(line);
                    }

                    JSONObject jsonResponse = getJsonResponse(buffer.toString());
                    osw.write(jsonResponse.toString());
                    osw.flush();
                } catch (SocketTimeoutException ste) {
                    /*Do nothing. Time is out. Wait for next client*/
                    ste.printStackTrace();
                } catch (IOException e) {
                    throw new FilePrinterLoggerServerException("FilePrinterLoggerServer IOExceptions", e);
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
     * @param port          Server will be waiting for clients on mentioned port
     * @param fileName      Output log filename
     * @param socketTimeout timeout in milliseconds, used in socket.setSoTimeout()
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
        if (isRuning) {
            return;
        }

        try {
            serverThread = new Thread(serverRunner);
            serverThread.start();
            isRuning = true;
        } catch (RuntimeException ex) {
            throw new LoggerException("Message ", ex);
        }
    }

    /**
     * Stop server thread
     */
    public void stop() {
        serverThread = null;
        isRuning = false;
    }

    /**
     * Check if the server is started
     *
     * @return if server is running then returns true, else false
     */
    public boolean isRunning() {
        return this.isRuning;
    }

    //region private methods
    private JSONObject getJsonResponse(String buffer) {
        JSONObject jsonRequest = new JSONObject(buffer);
        JSONObject jsonResponse = new JSONObject();

        if (!jsonRequest.keySet().contains(FIELD_MESSAGE)) {
            jsonResponse.put(FIELD_STATUS, FIELD_ERROR);
            jsonResponse.put(FIELD_ERROR, "unknown request");
            return jsonResponse;
        }

        Object obj = jsonRequest.get(FIELD_MESSAGE);

        String msg = (String) obj;
        jsonResponse.put(FIELD_STATUS, "ok");
        jsonResponse.put(FIELD_HASH, msg.hashCode());

        try {
            logger.log(msg, false);
            logger.close();
        } catch (LoggerException ex) {
            jsonResponse.put(FIELD_STATUS, FIELD_ERROR);
            jsonResponse.put(FIELD_ERROR, ex.toString());
            ex.printStackTrace();
        }
        return jsonResponse;
    }
    //endregion
}
