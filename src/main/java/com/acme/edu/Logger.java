package com.acme.edu;

public class Logger {

    //region fields
    public static final String LOG_PRIMITIVE = "primitive";
    public static final String LOG_CHAR = "char";
    public static final String LOG_STRING = "string";
    public static final String LOG_REFERENCE = "reference";

    private static int sum = Integer.MIN_VALUE;

    //endregion

    //region entry point

    /**
     * Entry Point
     *
     * @param argv class arguments
     */
    public static void main(String[] argv) {

    }

    //endregion

    //region public methods

    /**
     * Print integer parameter to log
     *
     * @param message print parameter
     */
    public static void log(int message) {
        if (sum == Integer.MIN_VALUE) {
            sum = 0;
        }

        if (message == Integer.MAX_VALUE) {
            print(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = Integer.MAX_VALUE;
        } else {
            sum += message;
        }
    }

    /**
     * Print char parameter to log
     *
     * @param message print parameter
     */
    public static void log(char message) {
        print(String.format("%s: %s", Logger.LOG_CHAR, message));
    }

    /**
     * Print boolean parameter to log
     *
     * @param message print parameter
     */
    public static void log(boolean message) {
        print(String.format("%s: %s", Logger.LOG_PRIMITIVE, message));
    }

    /**
     * Print String parameter to log
     *
     * @param message print parameter
     */
    public static void log(String message) {
        if (sum != Integer.MIN_VALUE) {
            close();
        }
        print(String.format("%s: %s", Logger.LOG_STRING, message));
    }

    /**
     * Print Object parameter to log
     *
     * @param message print parameter
     */
    public static void log(Object message) {
        print(String.format("%s: %s", Logger.LOG_REFERENCE, message));
    }

    //endregion

    //region private methods

    public static void close() {
        print(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
        sum = Integer.MIN_VALUE;
    }

    private static void print(String str) {
        System.out.println(str);
    }
    //endregion
}
