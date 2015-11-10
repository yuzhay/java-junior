package com.acme.edu.loggers;

import com.acme.edu.exceptions.FilePrinterLoggerServerException;
import com.acme.edu.exceptions.LoggerException;
import com.acme.edu.exceptions.PrinterException;
import com.acme.edu.printers.FilePrinter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Yuriy on 06.11.2015.
 * Network version of Printer class which is used in Logger class.
 */
public class LoggerServer {
    //region public fields
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_HASH = "hash";
    public static final String FIELD_ERROR = "error";
    //endregion

    //region private fields
    private final Charset charset = Charset.forName("utf-8");
    private ServerSocket socket;
    private Logger logger;
    private Thread serverThread;
    private Accepter accepter = new Accepter();

    //region Accepter
    private class Accepter implements Runnable {
        //region private fields
        private List<IOException> exceptionsList = new ArrayList<>();
        private ExecutorService pool = Executors.newFixedThreadPool(10);

        private class Worker implements Runnable {
            private Socket client;

            public Worker(Socket client) {
                this.client = client;
            }

            @Override
            public void run() {
                try (
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
                } catch (IOException e) {
                    exceptionsList.add(e);
                    e.printStackTrace();
                }
            }
        }
        //endregion

        //region public methods

        /**
         * Get exception list of acception and worker
         *
         * @return list of exception
         */
        public List<IOException> getExceptions() {
            return exceptionsList;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Socket client = socket.accept();
                    pool.execute(new Worker(client));
                } catch (SocketTimeoutException ste) {
                    /*Do nothing. Time is out. Wait for next client*/
                } catch (IOException e) {
                    exceptionsList.add(e);
                }
            }
            /*TODO: Make kindly shutdown*/
            pool.shutdownNow();
        }

        //endregion
    }
    //endregion
    //endregion

    //region constructor

    /**
     * Creates FilePrinterLoggerServer
     *
     * @param port          Accepter will be waiting for clients on mentioned port
     * @param fileName      Output log filename
     * @param socketTimeout timeout in milliseconds, used in socket.setSoTimeout()
     * @throws LoggerException
     */
    public LoggerServer(int port, String fileName, int socketTimeout) throws LoggerException {
        try {
            socket = new ServerSocket(port);
            socket.setSoTimeout(socketTimeout);
            logger = new Logger(new FilePrinter(fileName));
        } catch (IOException e) {
            throw new LoggerException("Accepter couldn't start", e);
        } catch (PrinterException e) {
            throw new LoggerException("Can't create server logger", e);
        }
    }
    //endregion

    //region public methods

    /**
     * Starts FilePrinterLogger server.
     *
     * @throws LoggerException
     */
    public void start() throws LoggerException {
        try {
            serverThread = new Thread(accepter);
            serverThread.start();
        } catch (RuntimeException ex) {
            throw new LoggerException("Message ", ex);
        }
    }

    /**
     * Stop server thread
     */
    public void stop() {
        serverThread.interrupt();
    }

    /**
     * Check if the server is started
     *
     * @return if server is running then returns true, else false
     */
    public boolean isRunning() {
        return serverThread.isInterrupted();
    }
    //endregion

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
