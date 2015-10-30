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
    public static final String LOG_PRIMITIVES_ARRAY = "primitives array";
    public static final String LOG_PRIMITIVES_MATRIX = "primitives matrix";
    public static final String LOG_PRIMITIVES_MULTIMATRIX = "primitives multimatrix";

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
            if ((long) message + (long) sum > Integer.MAX_VALUE) {
                print(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
                sum = message;
            } else {
                sum += message;
            }
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

    /**
     * Close log in order to print result
     */
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
            if (!lastStr.isEmpty()) {
                print(String.format("%s: %s", Logger.LOG_STRING, lastStr));
            }
            lastStr = "";
        }
    }

    /**
     * Print sum of int array to log
     *
     * @param message print parameter
     */
    public static void log(int... message) {
        int sum = 0;
        for (int aMessage : message) {
            sum += aMessage;
        }
        print(String.format("%s: %d", Logger.LOG_PRIMITIVE, sum));
    }


    /**
     * Print two dimensions int array (matrix) to log
     *
     * @param message print parameter
     */
    public static void log(int[][] message) {
        StringBuilder sb = new StringBuilder();
        sb.append('{').append(System.lineSeparator());
        for (int[] aMessage : message) {
            sb.append('{');
            for (int j = 0; j < aMessage.length; j++) {
                sb.append(aMessage[j]);
                if (j < aMessage.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append('}').append(System.lineSeparator());
        }
        sb.append('}');
        print(String.format("%s: %s", Logger.LOG_PRIMITIVES_MATRIX, sb.toString()));
    }

    /**
     * Print 4 dimensions int array (matrix) to log
     *
     * @param message print parameter
     */
    public static void log(int[][][][] message) {
        StringBuilder sb = new StringBuilder();
        String sep = System.lineSeparator();
        sb.append('{').append(sep);
        for (int[][][] aMessage : message) {
            sb.append('{').append(sep);
            for (int[][] anAMessage : aMessage) {
                sb.append('{').append(sep);
                for (int[] anAnAMessage : anAMessage) {
                    sb.append('{').append(sep);
                    int m = 0;
                    while (m < anAnAMessage.length) {
                        sb.append(anAnAMessage[m]);
                        if (m < anAnAMessage.length - 1) {
                            sb.append(", ");
                        }
                        m++;
                    }
                    sb.append(sep).append('}').append(sep);
                }
                sb.append('}').append(sep);
            }
            sb.append('}').append(sep);
        }
        sb.append('}');
        print(String.format("%s: %s", Logger.LOG_PRIMITIVES_MULTIMATRIX, sb.toString()));
    }

    /**
     * Print String array to log
     *
     * @param message print parameter
     */
    public static void log(String... message) {
        StringBuilder sb = new StringBuilder();
        for (String aMessage : message) {
            sb.append(aMessage).append(System.lineSeparator());
        }
        print(String.format("%s: %s", Logger.LOG_STRING, sb.toString()));
    }
    //endregion

    //region private methods

    private Logger() {
    }

    private static void print(String str) {
        System.out.println(str);
    }
    //endregion
}
