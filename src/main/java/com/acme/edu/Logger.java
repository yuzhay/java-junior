package com.acme.edu;

/**
 * Smart logger.
 */
public class Logger {

    //region fields
    public static final String LOG_PRIMITIVE = "primitive";
    public static final String LOG_CHAR = "char";
    public static final String LOG_STRING = "string";
    public static final String LOG_REFERENCE = "reference";

    private static int sum = Integer.MIN_VALUE;
    private static int strCounter = 1;
    private static String lastStr = "";

    //endregion

    //region public methods

    /**
     * Print integer parameter to log
     *
     * @param message print parameter
     */
    public static void log(int message) {
        if (!lastStr.isEmpty()) {
            close();
        }

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
     * Print byte parameter to log
     *
     * @param message print parameter
     */
    public static void log(byte message) {
        if (!lastStr.isEmpty()) {
            close();
        }

        if (sum == Integer.MIN_VALUE) {
            sum = 0;
        }

        if (message == Byte.MAX_VALUE) {
            print(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = Byte.MAX_VALUE;
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

        if (lastStr.equals(message)) {
            strCounter++;
        } else {
            strCounter = 1;
            if (!lastStr.isEmpty()) {
                print(String.format("%s: %s", Logger.LOG_STRING, lastStr));
            }
        }


        lastStr = message;
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
        if (sum != Integer.MIN_VALUE) {
            print(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = Integer.MIN_VALUE;
        }

        if (strCounter > 1) {
            print(String.format("%s: %s (x%d)", Logger.LOG_STRING, lastStr, strCounter));
            strCounter = 1;
            lastStr = "";
        } else if (strCounter == 1) {
            if (!lastStr.equals("")) {
                print(String.format("%s: %s", Logger.LOG_STRING, lastStr));
            }
            lastStr = "";
        }
    }

    private static void print(String str) {
        System.out.println(str);
    }
    //endregion
}
