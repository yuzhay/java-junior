package com.acme.edu;

import com.acme.edu.decorators.FormatDecoratorCommand;
import com.acme.edu.decorators.PrefixDecoratorCommand;
import com.acme.edu.exceptions.DecoratorException;
import com.acme.edu.exceptions.LoggerException;
import com.acme.edu.printers.Printer;
import com.acme.edu.printers.SimplePrinter;
import com.acme.edu.states.*;

/**
 * Smart simple logger.
 * Created by Yuriy on 02.11.2015.
 */
public class SimpleLogger {

    //region public fields
    public static final String LOG_PRIMITIVE = "primitive: ";
    public static final String LOG_CHAR = "char: ";
    public static final String LOG_STRING = "string: ";

    public static final String MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS = "Message not logged. Invalid decorator arguments.";
    //endregion

    //region private fields
    private State curState = null;
    private State intState = new IntState();
    private State stringState = new StringState();
    private State charState = new CharState();
    private State boolState = new BoolState();

    private Printer[] printers;

    //endregion

    //region constructor

    /**
     * Creates SimpleLogger instance
     */
    public SimpleLogger() {
        printers = new Printer[]{new SimplePrinter()};
    }

    /**
     * Creates SimpleLogger instance
     */
    public SimpleLogger(Printer... printers) throws LoggerException {
        if (printers == null || printers.length == 0) {
            throw new LoggerException("No printers defined");
        }
        this.printers = printers;
    }
    //endregion

    //region public methods

    /**
     * Print integer parameter to log
     *
     * @param message print parameter
     */
    public void log(int message) throws LoggerException {
        initState(intState);
        try {
            PrefixDecoratorCommand decor = new PrefixDecoratorCommand(
                    SimpleLogger.LOG_PRIMITIVE, printers);
            curState = curState.switchState(intState, String.valueOf(message), decor);
        } catch (DecoratorException ex) {
            throw new LoggerException(MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS, ex);
        }
    }

    /**
     * Print String parameter to log
     *
     * @param message print parameter
     */
    public void log(String message) throws LoggerException {
        initState(stringState);
        try {
            PrefixDecoratorCommand decor = new PrefixDecoratorCommand(
                    SimpleLogger.LOG_STRING, printers);
            curState = curState.switchState(stringState, message, decor);
        } catch (DecoratorException ex) {
            throw new LoggerException(MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS, ex);
        }
    }

    /**
     * Print char parameter to log
     *
     * @param message print parameter
     */
    public void log(char message) throws LoggerException {
        initState(charState);
        try {
            PrefixDecoratorCommand decor = new PrefixDecoratorCommand(
                    SimpleLogger.LOG_CHAR, printers);

            curState = curState.switchState(charState, String.valueOf(message), decor);
        } catch (DecoratorException ex) {
            throw new LoggerException(MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS, ex);
        }
    }

    /**
     * Print boolean parameter to log
     *
     * @param message print parameter
     */
    public void log(boolean message) throws LoggerException {
        initState(boolState);

        try {
            PrefixDecoratorCommand decor = new PrefixDecoratorCommand(
                    SimpleLogger.LOG_PRIMITIVE, printers);
            curState = curState.switchState(boolState, String.valueOf(message), decor);
        } catch (DecoratorException ex) {
            throw new LoggerException(MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS, ex);
        }
    }


    /**
     * Print int array parameter to log
     *
     * @param messages print parameter
     */
    public void log(int... messages) throws LoggerException {
        long sum = 0;
        initState(intState);
        try {
            PrefixDecoratorCommand decor = new PrefixDecoratorCommand(
                    SimpleLogger.LOG_PRIMITIVE, printers);

            for (int message : messages) {
                sum += message;
            }

            curState = curState.switchState(intState, String.valueOf(sum), decor);
            curState.flush();
        } catch (DecoratorException ex) {
            throw new LoggerException(MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS, ex);
        }
    }

    /**
     * Print 2d int array parameter to log
     *
     * @param messages print parameter
     */
    public void log(int[]... messages) throws LoggerException {
        initState(intState);
        try {
            PrefixDecoratorCommand decor = new PrefixDecoratorCommand(
                    SimpleLogger.LOG_PRIMITIVE, printers);
            long sum = get2dSum(messages);
            curState = curState.switchState(intState, String.valueOf(sum), decor);
            curState.flush();
        } catch (DecoratorException ex) {
            throw new LoggerException(MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS, ex);
        }
    }


    /**
     * Print 4d int array parameter to log
     *
     * @param messages print parameter
     */
    public void log(int[][][]... messages) throws LoggerException {
        long sum = 0;
        initState(intState);
        try {
            PrefixDecoratorCommand decor = new PrefixDecoratorCommand(
                    SimpleLogger.LOG_PRIMITIVE, printers);

            for (int[][][] message : messages) {
                for (int[][] aMessage : message) {
                    sum += get2dSum(aMessage);
                }
            }
            curState = curState.switchState(intState, String.valueOf(sum), decor);

            curState.flush();
        } catch (DecoratorException ex) {
            throw new LoggerException(MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS, ex);
        }
    }

    /**
     * Print string array parameter to log
     *
     * @param messages print parameter
     */
    public void log(String... messages) throws LoggerException {
        initState(stringState);
        try {
            FormatDecoratorCommand decor = new FormatDecoratorCommand(
                    SimpleLogger.LOG_STRING + "%s", printers);

            for (String message : messages) {
                curState = curState.switchState(stringState, String.valueOf(message), decor);
            }

            curState.flush();
        } catch (DecoratorException ex) {
            throw new LoggerException(MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS, ex);
        }
    }

    /**
     * Close log in order to print result
     */
    public void close() throws LoggerException {
        try {
            curState.flush();
        } catch (DecoratorException ex) {
            throw new LoggerException(MESSAGE_NOT_LOGGED_INVALID_DECORATOR_ARGUMENTS, ex);
        }
    }

    //endregion

    //region private methods
    private long get2dSum(int[][] messages) {
        long sum = 0;
        for (int[] message : messages) {
            for (int aMessage : message) {
                sum += aMessage;
            }
        }
        return sum;
    }

    private void initState(State st) {
        if (curState == null) {
            curState = st;
        }
    }
    //endregion

}
