package com.acme.edu;

/**
 * State interface for SimpleLogger
 * Created by Yuriy on 02.11.2015.
 */
public interface State {
    void log(String msg);

    void flush();
}
