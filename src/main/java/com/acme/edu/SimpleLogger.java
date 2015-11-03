package com.acme.edu;

/**
 * Smart simple logger.
 * Created by Yuriy on 02.11.2015.
 */
public class SimpleLogger {

    //region public fields
    public static final String LOG_PRIMITIVE = "primitive: ";
    public static final String LOG_CHAR = "char: ";
    public static final String LOG_STRING = "string: ";
    public static final String LOG_REFERENCE = "reference: ";
    public static final String LOG_PRIMITIVES_MATRIX = "primitives matrix: ";
    public static final String LOG_PRIMITIVES_MULTIMATRIX = "primitives multimatrix: ";
    //endregion

    //region private fields
    private State curState;
    private State intState;
    private State stringState;
    private State charState;
    private State boolState;

    private DecoratorCommand prevDecorator;
    //endregion

    //region constructor

    /**
     * Creates SimpleLogger instance
     */
    public SimpleLogger() {
        this.intState = new IntState();
        this.stringState = new StringState();
        this.charState = new CharState();
        this.boolState = new BoolState();
        this.curState = null;
    }
    //endregion

    //region public methods

    /**
     * Print integer parameter to log
     *
     * @param message print parameter
     */
    public void log(int message) {
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);
        changeState(intState, String.valueOf(message), decor);
    }

    /**
     * Print String parameter to log
     *
     * @param message print parameter
     */
    public void log(String message) {
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_STRING);
        changeState(stringState, message, decor);
    }

    /**
     * Print char parameter to log
     *
     * @param message print parameter
     */
    public void log(char message) {
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_CHAR);
        changeState(charState, String.valueOf(message), decor);
    }

    /**
     * Print boolean parameter to log
     *
     * @param message print parameter
     */
    public void log(boolean message) {
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);
        changeState(boolState, String.valueOf(message), decor);
    }


    /**
     * Print int array parameter to log
     *
     * @param messages print parameter
     */
    public void log(int... messages) {
        long sum = 0;
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);

        for (int i = 0; i < messages.length; i++) {
            sum += messages[i];
        }

        changeState(intState, String.valueOf(sum), decor);
        intState.flush(decor);
    }

    /**
     * Print 2d int array parameter to log
     *
     * @param messages print parameter
     */
    public void log(int[]... messages) {
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);
        long sum = get2dSum(messages);
        changeState(intState, String.valueOf(sum), decor);
        intState.flush(decor);
    }


    /**
     * Print 4d int array parameter to log
     *
     * @param messages print parameter
     */
    public void log(int[][][]... messages) {
        long sum = 0;
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);

        for (int i = 0; i < messages.length; i++) {
            for (int j = 0; j < messages[i].length; j++) {
                sum += get2dSum(messages[i][j]);
            }
        }
        changeState(intState, String.valueOf(sum), decor);

        intState.flush(decor);
    }

    /**
     * Print string array parameter to log
     *
     * @param messages print parameter
     */
    public void log(String... messages) {
        FormatDecoratorCommand decor = new FormatDecoratorCommand(
                new SimplePrinter(), SimpleLogger.LOG_STRING + "%s");

        for (int i = 0; i < messages.length; i++) {
            changeState(stringState, String.valueOf(messages[i]), decor);
        }

        stringState.flush(decor);
    }

    /**
     * Close log in order to print result
     */
    public void close() {
        curState.flush(prevDecorator);
    }

    //endregion

    //region private methods
    private void changeState(State st, String message, DecoratorCommand decor) {
        if (curState != null && curState != st) {
            curState.flush(prevDecorator);
        }

        curState = st;
        prevDecorator = decor;
        curState.log(message, decor);
    }

    private long get2dSum(int[][] messages) {
        long sum = 0;
        for (int i = 0; i < messages.length; i++) {
            for (int j = 0; j < messages[i].length; j++) {
                sum += messages[i][j];
            }
        }
        return sum;
    }
    //endregion
}
