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
    //endregion

    //region private fields
    private State curState = null;
    private State intState = new IntState();
    private State stringState = new StringState();
    private State charState = new CharState();
    private State boolState = new BoolState();

    //endregion

    //region constructor

    /**
     * Creates SimpleLogger instance
     */
    public SimpleLogger() {

    }
    //endregion

    //region public methods

    /**
     * Print integer parameter to log
     *
     * @param message print parameter
     */
    public void log(int message) {
        initState(intState);
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);
        curState = curState.switchState(intState, String.valueOf(message), decor);
    }

    /**
     * Print String parameter to log
     *
     * @param message print parameter
     */
    public void log(String message) {
        initState(stringState);
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_STRING);
        curState = curState.switchState(stringState, message, decor);
    }

    /**
     * Print char parameter to log
     *
     * @param message print parameter
     */
    public void log(char message) {
        initState(charState);
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_CHAR);
        curState = curState.switchState(charState, String.valueOf(message), decor);
    }

    /**
     * Print boolean parameter to log
     *
     * @param message print parameter
     */
    public void log(boolean message) {
        initState(boolState);
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);
        curState = curState.switchState(boolState, String.valueOf(message), decor);
    }


    /**
     * Print int array parameter to log
     *
     * @param messages print parameter
     */
    public void log(int... messages) {
        long sum = 0;
        initState(intState);
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);

        for (int i = 0; i < messages.length; i++) {
            sum += messages[i];
        }

        curState = curState.switchState(intState, String.valueOf(sum), decor);
        curState.flush();
    }

    /**
     * Print 2d int array parameter to log
     *
     * @param messages print parameter
     */
    public void log(int[]... messages) {
        initState(intState);
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);
        long sum = get2dSum(messages);
        curState = curState.switchState(intState, String.valueOf(sum), decor);
        curState.flush();
    }


    /**
     * Print 4d int array parameter to log
     *
     * @param messages print parameter
     */
    public void log(int[][][]... messages) {
        long sum = 0;
        initState(intState);
        PrefixDecoratorCommand decor = new PrefixDecoratorCommand(new SimplePrinter(), SimpleLogger.LOG_PRIMITIVE);

        for (int i = 0; i < messages.length; i++) {
            for (int j = 0; j < messages[i].length; j++) {
                sum += get2dSum(messages[i][j]);
            }
        }
        curState = curState.switchState(intState, String.valueOf(sum), decor);

        curState.flush();
    }

    /**
     * Print string array parameter to log
     *
     * @param messages print parameter
     */
    public void log(String... messages) {
        initState(stringState);
        FormatDecoratorCommand decor = new FormatDecoratorCommand(
                new SimplePrinter(), SimpleLogger.LOG_STRING + "%s");

        for (int i = 0; i < messages.length; i++) {
            curState = curState.switchState(stringState, String.valueOf(messages[i]), decor);
        }

        curState.flush();
    }

    /**
     * Close log in order to print result
     */
    public void close() {
        curState.flush();
    }

    //endregion

    //region private methods
    private long get2dSum(int[][] messages) {
        long sum = 0;
        for (int i = 0; i < messages.length; i++) {
            for (int j = 0; j < messages[i].length; j++) {
                sum += messages[i][j];
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
