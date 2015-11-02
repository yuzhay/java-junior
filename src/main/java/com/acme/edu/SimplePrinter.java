package com.acme.edu;

/**
 * Printer interface for SimpleLogger
 * Created by Yuriy on 02.11.2015.
 */
public class SimplePrinter implements Printer {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
