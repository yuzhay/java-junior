package com.acme.edu;

/**
 * State interface for SimpleLogger
 * Created by Yuriy on 02.11.2015.
 */
public interface State {

    /**
     * Print message to log
     *
     * @param msg print parameter
     */
    void log(String msg);

    /**
     * Flush in order to print buffer result to log
     */
    void flush();
}
