package com.acme.edu.exceptions;

/**
 * Created by Yuriy on 07.11.2015.
 * Default Exception for FilePrinterLoggerServer class
 */
public class FilePrinterLoggerServerException extends RuntimeException {
    /**
     * FilePrinterLoggerServerException constructor used in Logger class.
     *
     * @param message this message will be shown as exception description
     * @param ex      inner exception
     */
    public FilePrinterLoggerServerException(String message, Exception ex) {
        super(message, ex);
    }

}
